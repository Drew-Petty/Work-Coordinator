package com.dp.workManager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dp.workManager.models.Role;
import com.dp.workManager.models.User;
import com.dp.workManager.repos.RoleRepo;
import com.dp.workManager.repos.UserRepo;

@Service
public class UserService {
	public static final int USERS_PER_PAGE =10;
	private UserRepo userRepo;
	private RoleRepo roleRepo;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserService(UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	public void saveWithUserRole(User user) {
		String hash = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(hash);
		user.setPasswordConfirmation(hash);
		user.setRoles(roleRepo.findByName("ROLE_USER"));
		userRepo.save(user);
	}
	public void saveWithAdminRole(User user) {
		String hash = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(hash);
		user.setPasswordConfirmation(hash);
		user.setRoles(roleRepo.findByName("ROLE_ADMIN"));
		userRepo.save(user);
	}
	public void saveWithRole(User user) {
		if(allUsers().size()==0) {
			saveWithAdminRole(user);
		}else {
			saveWithUserRole(user);
		}
	}
	
	public User findByEmail(String email) {
		Optional<User> user = userRepo.findByEmail(email);
		if(user.isPresent()) {
			return user.get();
		}else {
			return null;
		}
	}
	public List<User> allUsers(){
		return (List<User>)userRepo.findAll();
	}
	public Page<User> listByPage(int pageNum){
		Pageable pageable = PageRequest.of(pageNum-1, USERS_PER_PAGE);
		return userRepo.findAll(pageable);
	}
	
	
	public User saveUser(User user) {
		return userRepo.save(user);
	}
	public void deleteUserById(Long id) {
		userRepo.deleteById(id);
	}
	public User findUserById(Long id) {
		Optional<User> optionalUser = userRepo.findById(id);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		}else {
			return null;
		}
	}
	public List<User> usersWithNoCrew(){
		return userRepo.findByCrewIsNull();
	}
	public Boolean isAdmin(User user) {
		Optional<Role> admin = roleRepo.findById((long) 1);
		List<Role> roles = user.getRoles();
		if(roles.contains(admin.get())) {
			return true;
		}else {
			return false;
		}
	}
	public Boolean isProfile(User user, User principal) {
		if(user.getId().equals(principal.getId())) {
			return true;
		}else {
			return false;
		}
	}
}
package com.dp.workManager.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dp.workManager.models.Role;
import com.dp.workManager.models.User;
import com.dp.workManager.repos.UserRepo;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService{
	private UserRepo userRepo;
	public UserDetailsServiceImplementation(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		Optional<User> optionalUser = userRepo.findByEmail(email);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user));			
		}else {
			throw new UsernameNotFoundException("User not found");
		}
	}
	private List<GrantedAuthority> getAuthorities(User user){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(Role role: user.getRoles()) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
			authorities.add(grantedAuthority);
		}
		return authorities;
	}
}

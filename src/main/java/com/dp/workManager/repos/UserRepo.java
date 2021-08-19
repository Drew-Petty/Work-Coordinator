package com.dp.workManager.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.dp.workManager.models.User;

@Repository
public interface UserRepo extends PagingAndSortingRepository<User, Long> {
	Optional<User> findByEmail(String email);
	Optional<User> findById(Long id);
	void deleteById(Long id);
	@SuppressWarnings("unchecked")
	User save(User user);
	List<User> findByCrewIsNull();
	Page<User> findAll(Pageable pageable); 
	
}

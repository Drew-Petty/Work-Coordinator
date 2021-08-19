package com.dp.workManager.repos;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.dp.workManager.models.Job;

@Repository
public interface JobRepo extends PagingAndSortingRepository<Job, Long> {
	Page<Job> findAll(Pageable pageable);
	Optional<Job> findById(Long id);
	void deleteById(Long id);
	@SuppressWarnings("unchecked")
	Job save(Job job);
}

package com.dp.workManager.repos;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.dp.workManager.models.Crew;

@Repository
public interface CrewRepo extends PagingAndSortingRepository<Crew, Long> {
	Page<Crew> findAll(Pageable pageable);
	Optional<Crew> findById(Long id);
	void deleteById(Long id);
	@SuppressWarnings("unchecked")
	Crew save(Crew crew);
}

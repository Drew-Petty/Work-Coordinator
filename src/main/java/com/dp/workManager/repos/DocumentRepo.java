package com.dp.workManager.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dp.workManager.models.Document;
import com.dp.workManager.models.Job;
import com.dp.workManager.models.User;

@Repository
public interface DocumentRepo extends JpaRepository<Document, Long> {
	List<Document> findAll();
	Optional<Document> findById(Long id);
	@SuppressWarnings("unchecked")
	Document save(Document document);
	List<Document> findByUsersContaining(User user);
	List<Document> findByJobsContaining(Job job);
}

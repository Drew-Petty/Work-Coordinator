package com.dp.workManager.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dp.workManager.exceptions.FileStorageException;
import com.dp.workManager.models.Document;
import com.dp.workManager.models.Job;
import com.dp.workManager.models.User;
import com.dp.workManager.repos.DocumentRepo;

@Service
public class DocumentService {
	private final DocumentRepo documentRepo;

	public DocumentService(DocumentRepo documentRepo) {
		this.documentRepo = documentRepo;
	}
	public void deleteDocumentById(Long id) {
		documentRepo.deleteById(id);
	}
	public Document findDocumentById(Long id) {
		Optional<Document> optionalDocument = documentRepo.findById(id);
		if(optionalDocument.isPresent()) {
			return optionalDocument.get();
		}else {
			return null;
		}
	}
	public void addUser(Document document, User user) {
		List<User> userList = document.getUsers();
		if (userList == null) {
			userList = new ArrayList<User>();
		}
		userList.add(user);
		document.setUsers(userList);
		documentRepo.save(document);
	}
	public void addJob(Document document, Job job) {
		List<Job> jobList = document.getJobs();
		if (jobList == null) {
			jobList = new ArrayList<Job>();
		}
		jobList.add(job);
		document.setJobs(jobList);
		documentRepo.save(document);
	}
	public Document saveDocument(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if(fileName.contains("..")) {
				throw new FileStorageException("file name contails invalid path sequence"+fileName);
			}
			Document document = new Document(fileName, file.getContentType(), file.getBytes());
			return documentRepo.save(document);
		}catch (IOException exception) {
			throw new FileStorageException("could not store file", exception);
		}
	}
	public boolean isNothing(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (fileName.isBlank()) {
			return true;
		} else {
			return false;
		}
	}
	
}

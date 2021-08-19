package com.dp.workManager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dp.workManager.models.Crew;
import com.dp.workManager.repos.CrewRepo;

@Service
public class CrewService {
	public static final int CREWS_PER_PAGE =10;
	private final CrewRepo crewRepo;

	public CrewService(CrewRepo crewRepo) {
		this.crewRepo = crewRepo;
	}
	
	public Crew saveCrew(Crew crew) {
		return crewRepo.save(crew);
	}
	public void deleteCrewById(Long id) {
		crewRepo.deleteById(id);
	}
	public Crew findCrewById(Long id) {
		Optional<Crew> optionalCrew = crewRepo.findById(id);
		if(optionalCrew.isPresent()) {
			return optionalCrew.get();
		}else {
			return null;
		}
	}
	public List<Crew> allCrews(){
		return (List<Crew>)crewRepo.findAll();
	}
	public Page<Crew> listByPage(int pageNum){
		Pageable pageable = PageRequest.of(pageNum-1, CREWS_PER_PAGE);
		return crewRepo.findAll(pageable);
	}
	
}
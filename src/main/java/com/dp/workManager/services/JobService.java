package com.dp.workManager.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dp.workManager.models.Crew;
import com.dp.workManager.models.Job;
import com.dp.workManager.repos.JobRepo;

@Service
public class JobService {
	public static final int JOBS_PER_PAGE =10;
	private final JobRepo jobRepo;
	private final CrewService crewService;

	public JobService(JobRepo jobRepo, CrewService crewService) {
		this.jobRepo = jobRepo;
		this.crewService= crewService;
	}
	public List<Job> allJobs() {
		return (List<Job>)jobRepo.findAll();
	}
	public Page<Job> listByPage(int pageNum){
		Pageable pageable = PageRequest.of(pageNum-1, JOBS_PER_PAGE);
		return jobRepo.findAll(pageable);
	}
	public Job saveJob(Job job) {
		return jobRepo.save(job);
	}
	public void deleteByJobId(Long id) {
		jobRepo.deleteById(id);
	}
	public Job findJobById(Long id) {
		Optional<Job> optionalJob = jobRepo.findById(id);
		if (optionalJob.isPresent()) {
			return optionalJob.get();
		}else {
			return null;
		}
	}
	public List<Crew> crewsWithoutConflict(Job job){
		List<Crew> allCrews = crewService.allCrews();
		List<Crew> available = new ArrayList<Crew>();
		for(Crew crew: allCrews) {
			if(!jobListConflict(crew.getJobs(), job)) {
				available.add(crew);
			}
		}
		return available;
	}
	public Boolean jobListConflict(List<Job> jobList, Job job) {
		for(Job j:jobList) {
			if(jobConflict(job, j)) {
				return true;
			}
		}
		return false;
	}
	public Boolean jobConflict(Job job, Job jobb) {
		if(job.getStartDate().compareTo(jobb.getEndDate())<=0 && jobb.getStartDate().compareTo(job.getStartDate())<=0) {
			return true;
		}else if (job.getEndDate().compareTo(jobb.getEndDate())<=0 && jobb.getStartDate().compareTo(job.getEndDate())<=0) {
			return true;
		}else if (jobb.getStartDate().compareTo(job.getEndDate())<=0 && job.getStartDate().compareTo(jobb.getStartDate())<=0) {
			return true;
		}else if (jobb.getEndDate().compareTo(job.getEndDate())<=0 && job.getStartDate().compareTo(jobb.getEndDate())<=0) {
			return true;
		}else {
			return false;
		}
	}
	public Boolean dayOverLap(List<Job> jobs, Calendar cal) {
		for(Job job:jobs) {
			if(OverLap(job, cal, cal)) {
				return true;
			}
		}
		return false;
	}
	
	public List<Job> weekOverlap(List<Job> jobs, Calendar start, Calendar end){
		List<Job> overlapList = new ArrayList<Job>();
		for(Job job:jobs) {
			if(OverLap(job, start, end)) {
				overlapList.add(job);
			}
		}
		return overlapList;
	}
	public Boolean OverLap(Job job, Calendar start, Calendar end) {
		if(job.getStartDate().compareTo(end)<=0 && start.compareTo(job.getStartDate())<=0) {
			return true;
		}else if(job.getEndDate().compareTo(end)<=0 && start.compareTo(job.getEndDate())<=0) {
			return true;
		}else if(start.compareTo(job.getEndDate())<=0 && job.getStartDate().compareTo(start)<=0) {
			return true;
		}else if(end.compareTo(job.getEndDate())<=0 && job.getStartDate().compareTo(end)<=0) {
			return true;
		}else {
			return false;
		}
	}
}

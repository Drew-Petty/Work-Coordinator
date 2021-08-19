package com.dp.workManager.layout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.dp.workManager.models.Job;
import com.dp.workManager.services.CrewService;
import com.dp.workManager.services.JobService;

public class CrewLayout {
	public List<Job> weekJobs;
	public List<Calendar> calendarList;
	public List<JobLayout> jobLayoutList;
	private JobService jobService;
	

	public CrewLayout(List<Job> weekJobs, List<Calendar> calendarList, CrewService crewService, JobService jobService) {
		this.weekJobs = weekJobs;
		this.calendarList = calendarList;
		this.jobService = jobService;
		this.jobLayoutList = makeJobLayoutList();
	}
	public List<JobLayout> makeJobLayoutList(){
		List<JobLayout> jobLayoutList = new ArrayList<JobLayout>();
		int count = 0;
		while(count<7) {
			if(!jobService.dayOverLap(weekJobs, calendarList.get(count))) {
				JobLayout day = new JobLayout(null, count+1, count+2, calendarList.get(count));
				jobLayoutList.add(day);
			}else {
				for(Job job:weekJobs) {
					if(jobService.OverLap(job, calendarList.get(count), calendarList.get(count))) {
						int start = count+1;
						int length = (int) Math.min(8-start,1+TimeUnit.MILLISECONDS.toDays(Math.abs(calendarList.get(count).getTimeInMillis()-job.getEndDate().getTimeInMillis())));
						JobLayout day = new JobLayout(job, start, start+length, null);
						jobLayoutList.add(day);
						count+=length-1;
					}
				}
			}
			count++;
		}
		return jobLayoutList;
	}
	public List<JobLayout> getJobLayoutList() {
		return jobLayoutList;
	}
	public void setJobLayoutList(List<JobLayout> jobLayoutList) {
		this.jobLayoutList = jobLayoutList;
	}
	
}

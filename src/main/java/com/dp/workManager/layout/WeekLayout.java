package com.dp.workManager.layout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.dp.workManager.models.Crew;
import com.dp.workManager.models.Job;
import com.dp.workManager.services.CrewService;
import com.dp.workManager.services.JobService;

public class WeekLayout {
	
	public List<Calendar> calendarList;
	public List<CrewLayout> crewLayoutList;

	public WeekLayout(List<Calendar> calendarList, CrewService crewService, JobService jobService) {
		this.calendarList = calendarList;
		this.crewLayoutList = new ArrayList<CrewLayout>();
		List<Crew> allCrews = crewService.allCrews();
		for(Crew crew:allCrews) {
			List<Job> jobs = crew.getJobs();
			List<Job> weekOverlap = jobService.weekOverlap(jobs, calendarList.get(0),calendarList.get(6));
			if(weekOverlap.size()> 0) {
				CrewLayout crewLayout = new CrewLayout(weekOverlap,calendarList,crewService,jobService);
				crewLayoutList.add(crewLayout);
			}
		}
	}

	public List<Calendar> getCalendarList() {
		return calendarList;
	}

	public void setCalendarList(List<Calendar> calendarList) {
		this.calendarList = calendarList;
	}

	public List<CrewLayout> getCrewLayoutList() {
		return crewLayoutList;
	}

	public void setCrewLayoutList(List<CrewLayout> crewLayoutList) {
		this.crewLayoutList = crewLayoutList;
	}
	
}

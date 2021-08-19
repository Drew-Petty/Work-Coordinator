package com.dp.workManager.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dp.workManager.layout.WeekLayout;

@Service
public class CalendarService {
	private final JobService jobService;
	private final CrewService crewService;
	
	public CalendarService(JobService jobService, CrewService crewService) {
		this.jobService = jobService;
		this.crewService = crewService;
	}
	public List<WeekLayout> getLayout(Calendar cal) {
		ArrayList<WeekLayout> monthLayout = new ArrayList<WeekLayout>();
		Calendar calendar = new Calendar.Builder().setCalendarType("iso8601").setDate(cal.get(1),cal.get(2),cal.get(5)).build();
		Integer month = calendar.get(2);
		calendar.set(5, 1);
		while(calendar.get(7)!=2) {
			calendar.add(5, -1);
		}
		List<Calendar> calendarList= new ArrayList<Calendar>();
		for(int count = 0; count<7; count++) {
			Calendar c = new Calendar.Builder().setCalendarType("iso8601").setDate(calendar.get(1), calendar.get(2), calendar.get(5)).build();
			calendarList.add(c);
			calendar.add(5, 1);
		}
		WeekLayout weekLayout = new WeekLayout(calendarList, crewService, jobService);
		monthLayout.add(weekLayout);
		while(month.equals(calendar.get(2))) {
			List<Calendar> cList= new ArrayList<Calendar>();
			for(int count = 0; count<7; count++) {
				Calendar c = new Calendar.Builder().setCalendarType("iso8601").setDate(calendar.get(1), calendar.get(2), calendar.get(5)).build();
				cList.add(c);
				calendar.add(5, 1);
			}
			WeekLayout wLayout = new WeekLayout(cList, crewService, jobService);
			monthLayout.add(wLayout);
		}
		return monthLayout;
	}
	
}

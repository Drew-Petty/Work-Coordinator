package com.dp.workManager.layout;

import java.util.Calendar;

import com.dp.workManager.models.Job;

public class JobLayout {
	public Job job;
	public Integer gridStart;
	public Integer gridEnd;
	public Calendar calendar;
	public JobLayout(Job job, Integer gridStart, Integer gridEnd, Calendar calendar) {
		this.job = job;
		this.gridStart = gridStart;
		this.gridEnd = gridEnd;
		this.calendar = calendar;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public Integer getGridStart() {
		return gridStart;
	}
	public void setGridStart(Integer gridStart) {
		this.gridStart = gridStart;
	}
	public Integer getGridEnd() {
		return gridEnd;
	}
	public void setGridEnd(Integer gridEnd) {
		this.gridEnd = gridEnd;
	}
	public Calendar getCalendar() {
		return calendar;
	}
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
	
	
}

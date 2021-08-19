package com.dp.workManager.models;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.dp.workManager.annotation.CheckDates;
import com.dp.workManager.annotation.InFuture;

@Entity
@Table(name="jobs")
@CheckDates(message = "End date must not be before start date.")
public class Job {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Size(min=1, message="please enter a valid job name.")
	private String name;
	@NotNull
	@Size(min = 1, message = "Please enter a valid location." )
	private String location;
	@NotNull(message = "Please select a start date.")
	@InFuture(message = "Start date must not be in the past.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar startDate;
	@NotNull(message = "Please select an end date.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar endDate;
	
	//===============================
	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
	//===============================
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="jobs_documents",
			joinColumns = @JoinColumn(name="job_id"),
			inverseJoinColumns = @JoinColumn(name="document_id")
			)
	private List<Document> documents;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="crew_id")
	private Crew crew;
	//===============================
	public Job(@NotNull @Size(min = 1, message = "please enter a valid job name.") String name,
			@NotNull @Size(min = 1, message = "Please enter a valid location.") String location, Calendar startDate,
			Calendar endDate) {
		this.name = name;
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public Job() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Calendar getStartDate() {
		return startDate;
	}
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	public Calendar getEndDate() {
		return endDate;
	}
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public List<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
	public Crew getCrew() {
		return crew;
	}
	public void setCrew(Crew crew) {
		this.crew = crew;
	}
	
	
}

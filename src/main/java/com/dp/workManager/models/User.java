package com.dp.workManager.models;

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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.dp.workManager.annotation.MatchPassword;
import com.dp.workManager.annotation.UniqueEmail;

@Entity
@Table(name="users")
@MatchPassword(message = "password and confirm password must match")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Size(min=2, message="Name must be greater than 1 characters")
	private String name;
	@UniqueEmail(message="Email already registered")
	@Email(message = "Email must be valid")
	@Size(min = 1, message = "Please enter a valid email." )
	private String email;
	@Size(min=8, message = "Password must be at least 8 characters")
	private String password;
	@Transient
	private String passwordConfirmation;
	
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
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="user_roles",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="role_id"))
	private List<Role> roles;
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="users_documents",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="document_id")
			)
	private List<Document> documents;
	@OneToOne(mappedBy = "lead", fetch = FetchType.LAZY)
	private Crew myCrew;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="crew_id")
	private Crew crew;
	//===============================
	public User(@Size(min = 4, message = "Name must be greater than 3 characters") String name,
			@Email(message = "Email must be valid") @Size(min = 1, message = "Please enter a valid email.") String email,
			@Size(min = 8, message = "Password must be at least 8 characters") String password,
			String passwordConfirmation) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
	}
	
	public User(@Size(min = 4, message = "Name must be greater than 3 characters") String name,
			@Email(message = "Email must be valid") @Size(min = 1, message = "Please enter a valid email.") String email) {
		this.name = name;
		this.email = email;
	}
	
	public User() {
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
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
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public List<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
	public Crew getMyCrew() {
		return myCrew;
	}
	public void setMyCrew(Crew myCrew) {
		this.myCrew = myCrew;
	}
	public Crew getCrew() {
		return crew;
	}
	public void setCrew(Crew crew) {
		this.crew = crew;
	}
	
	
	
	
}

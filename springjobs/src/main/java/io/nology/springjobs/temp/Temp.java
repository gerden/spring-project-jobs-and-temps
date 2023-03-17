package io.nology.springjobs.temp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.nology.springjobs.job.Job;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Temp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column
	String firstName;

//	@NotBlank
	@Column
	String lastName;

//	@OneToMany(mappedBy = "Temp")	
//	List<Jobs> jobs;
//	List<Jobs> jobs = new List<Jobs>();
	
//	@OneToMany
	
	
	@OneToMany(mappedBy = "temp", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Column(nullable = true)
	Set<Job> jobs;
	
	
	public Temp() {
	}
	
	public Temp(String firstName, String lastName, Set<Job> jobs) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.jobs = jobs;
	}

	
	
	public Set<Job> getJobs() {
		return jobs;
	}

	public void setJobs(Set<Job> jobs) {
		this.jobs = jobs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}

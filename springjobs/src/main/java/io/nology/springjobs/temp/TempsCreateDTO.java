package io.nology.springjobs.temp;

import java.util.ArrayList;
import java.util.Set;

import io.nology.springjobs.job.Job;

public class TempsCreateDTO {
	
	String firstName;
	String lastName;
	Set<Job> jobs;
	
//	public TempsCreateDTO(String firstName, String lastName){
//		super();
//		this.firstName = firstName;
//		this.lastName = lastName;
//		
//	}
	
	public TempsCreateDTO(String firstName, String lastName, Set<Job> jobs){
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.jobs = jobs;
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

	public Set<Job> getJobs() {
		return jobs;
	}

	public void setJobs(Set<Job> jobs) {
		this.jobs = jobs;
	}

	
	
	
}

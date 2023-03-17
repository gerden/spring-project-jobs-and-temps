package io.nology.springjobs.job;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.nology.springjobs.temp.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column
	String name;

//	@NotBlank
	@Column
	LocalDateTime StartDate;

	@Column
	LocalDateTime EndDate;

//	@Column
//	@ManyToOne(cascade = CascadeType.ALL)
////	@JoinColumn( name = "temp_id", referencedColumnName = "id")
//	Temps Temp;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "temp", nullable = true)
	Temp temp;


//	referencedColumnName = “id"

	

	public Job() {
	}
	
	public Job(String name, LocalDateTime startDate, LocalDateTime endDate) {
		super();
		this.name = name;
		StartDate = startDate;
		EndDate = endDate;
	}
	
	public Job(String name, LocalDateTime startDate, LocalDateTime endDate, Temp temp) {
		super();
		this.name = name;
		StartDate = startDate;
		EndDate = endDate;
		
		List<Job> jobs = new ArrayList(temp.getJobs());
		
		Boolean validTemp = true;
		
		for(int i = 0; i < jobs.size(); i++) {
			
			if(!(!(StartDate.isBefore(jobs.get(i).getEndDate())) || !(EndDate.isAfter(jobs.get(i).getStartDate())))) {
				System.out.println("not valid");
				validTemp = false;
				i = jobs.size();
			}
		}

		if (validTemp) {
			this.temp = temp;
		}
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


	public LocalDateTime getStartDate() {
		return StartDate;
	}


	public void setStartDate(LocalDateTime startDate) {
		StartDate = startDate;
	}


	public LocalDateTime getEndDate() {
		return EndDate;
	}


	public void setEndDate(LocalDateTime endDate) {
		EndDate = endDate;
	}

	public Temp getTemp() {
		return temp;
	}

	public void setTemp(Temp temp) {
		this.temp = temp;
	}



}

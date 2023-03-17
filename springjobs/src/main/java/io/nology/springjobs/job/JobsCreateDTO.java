package io.nology.springjobs.job;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

import io.nology.springjobs.temp.*;

public class JobsCreateDTO {
	@NotBlank
	String name;

//	@NotBlank
	LocalDateTime StartDate;

	LocalDateTime EndDate;
	
	@Nullable
	Long temp;

	
	public JobsCreateDTO(@NotBlank String name, @NotBlank String startDate, String endDate) {
		super();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		this.name = name;
		StartDate = LocalDateTime.parse(startDate, formatter);
		EndDate = LocalDateTime.parse(endDate, formatter);
		
		
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


	public Long getTemp() {
		return temp;
	}


	public void setTemp(Long temp) {
		this.temp = temp;
	}
	
	

}

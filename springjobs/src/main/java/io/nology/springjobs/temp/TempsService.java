package io.nology.springjobs.temp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.nology.springjobs.job.Job;
import io.nology.springjobs.job.JobsRepository;

@Service
public class TempsService {
	
	

	@Autowired
	private TempsRepository tempRepository;
	
	@Autowired
	private JobsRepository jobRepository;
	
	public Temp create(TempsCreateDTO data) {
		String firstName = data.getFirstName();
		String lastName = data.getLastName();
		Set<Job> jobs = data.getJobs();
		Temp newTemp = new Temp(firstName,lastName, jobs);
		this.tempRepository.save(newTemp);
		return newTemp;
	}
	
	public List<Temp> all() {
		return this.tempRepository.findAll();		
	}	
	
	public Optional<Temp> find(long id) {
		return this.tempRepository.findById(id);		
	}
	
	public Temp delete(long id) {
		Optional<Temp> maybeTemp = this.find(id);
//		this.repository.delete(maybeJob);
		
		if (maybeTemp.isEmpty()) {
			return null;
		}
		this.tempRepository.delete(maybeTemp.get());
		return maybeTemp.get(); 
	}
	
	public List<Temp> availableTempsForJob(long id) {
		
		List<Temp> allTemps = this.tempRepository.findAll();
		List<Temp> validTemps = new ArrayList<Temp>();
		
		for(int i = 0; i < allTemps.size(); i++) {


			Job[] tempJobs = allTemps.get(i).getJobs().toArray(new Job[allTemps.get(i).getJobs().size()]);

			
			Boolean tempAvailable = true;

			for(int j = 0; j < tempJobs.length; j++) {
	
				if(!(!(tempJobs[j].getStartDate().isBefore(jobRepository.getById(id).getEndDate())) || !(tempJobs[j].getEndDate().isAfter(jobRepository.getById(id).getStartDate())))) {
					tempAvailable = false;
				}

				
			}
			if (tempAvailable) {
				validTemps.add(allTemps.get(i));
			}
			

		
		}
		
		
		return validTemps;		
	}
	
}

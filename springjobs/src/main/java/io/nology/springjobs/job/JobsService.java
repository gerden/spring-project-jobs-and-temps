package io.nology.springjobs.job;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import io.nology.springjobs.temp.Temp;
import io.nology.springjobs.temp.TempsRepository;

@Service
//@Transactional
public class JobsService {

	@Autowired
	private JobsRepository repository;
	
	@Autowired
	private TempsRepository tempRepository;
	
	public Job create(JobsCreateDTO data) {
		String cleanName = data.getName().trim();
		LocalDateTime cleanSDate;
		LocalDateTime cleanEDate;
		
		// makes sure start date is before end date by reversing if not
		if(data.getStartDate().isBefore(data.getEndDate())) {
			cleanSDate = data.getStartDate();
			cleanEDate = data.getEndDate();
			
		}
		else {
			cleanSDate = data.getEndDate();
			cleanEDate = data.getStartDate();
			
		}
		if(data.getTemp() == null) {
			Job newJob = new Job(cleanName,cleanSDate,cleanEDate);	
//			this.repository.save(newJob);		
			return this.repository.save(newJob);		
		}
		else {
			Long cleanTemp = data.getTemp();
			Optional<Temp> maybeTemp = this.tempRepository.findById(cleanTemp);
			if(maybeTemp.isEmpty()) {
				Job newJob = new Job(cleanName,cleanSDate,cleanEDate);
				return this.repository.save(newJob);
			}
			else {
				Job newJob = new Job(cleanName,cleanSDate,cleanEDate,maybeTemp.get());
				return this.repository.save(newJob);
			}		
		}

	}
	
	public List<Job> all() {
		long id = 0;
		return this.repository.findAll();		
	}	
	
	public Optional<Job> find(long id) {
		return this.repository.findById(id);
	}

	public List<Job> tempAssigned(long id, Boolean assigned) {

		List<Job> allJobs = this.repository.findAll();
		List<Job> validJobs = new ArrayList<Job>();
		
		for(int i = 0; i < allJobs.size(); i++) {
			if(allJobs.get(i).getTemp() != null) {
				if((allJobs.get(i).getTemp().getId() == id) == assigned) {
					System.out.println(allJobs.get(i).getId());
					validJobs.add(allJobs.get(i));
				}
			}
		}
		return validJobs;	
	}

	
	public Job delete(long id) {
		Optional<Job> maybeJob = this.find(id);

		System.out.println(this.repository.count());
		if (maybeJob.isEmpty()) {
			return null;
		}
		
		Job realJob = maybeJob.get();
		
		if(realJob.getTemp() != null) {
			realJob.setTemp(null);			
			this.repository.save(realJob);
		}		
		
		this.repository.deleteById(realJob.getId());
		return realJob; 
	}
	
	public Job changeName(long id) {
		Optional<Job> maybeJob = this.find(id);
//		this.repository.delete(maybeJob);
		
		if (maybeJob.isEmpty()) {
			return null;
		}
//		this.repository.delete(maybeJob.get());
		maybeJob.get().setName("test11111111111");;
//		realJob.setName("test2");
		this.repository.save(maybeJob.get());
		return maybeJob.get(); 
	}
	
	public Job changeTemp(long id, long tempId) {
		Optional<Job> maybeJob = this.find(id);
		Optional<Temp> maybeTemp = tempRepository.findById(tempId);
		
		
		Boolean tempAvailable = true;


		Job[] tempsJobs = maybeTemp.get().getJobs().toArray(new Job[maybeTemp.get().getJobs().size()]);	
		
		for(int i = 0; i < tempsJobs.length ; i++) {	
			
			if((tempsJobs[i].StartDate.isBefore(maybeJob.get().EndDate)) || (tempsJobs[i].EndDate.isBefore(maybeJob.get().StartDate))) {
				tempAvailable = false;
			}
			
		}
		
		
		if (maybeJob.isEmpty()) {
			return null;
		}
		if (maybeTemp.isEmpty()) {
			return null;
		}
		if(tempAvailable == false) {
			return null;
		}

		maybeJob.get().setTemp(maybeTemp.get());
		this.repository.save(maybeJob.get());
		return maybeJob.get(); 
	}

	public Job updateJob(long id, @Valid JobsCreateDTO data) {
		Optional<Job> maybeJob = this.find(id);
		Optional<Temp> maybeTemp = tempRepository.findById(data.getTemp());

		
		
		Boolean tempAvailable = true;


		Job[] tempsJobs = maybeTemp.get().getJobs().toArray(new Job[maybeTemp.get().getJobs().size()]);	
		
		for(int i = 0; i < tempsJobs.length ; i++) {	
			
			if((tempsJobs[i].StartDate.isBefore(maybeJob.get().EndDate)) || (tempsJobs[i].EndDate.isBefore(maybeJob.get().StartDate))) {
				tempAvailable = false;
			}
			
		}
		
		
		if (maybeJob.isEmpty()) {
			return null;
		}

		if (maybeTemp.isEmpty()) {
			return null;
		}

		if(tempAvailable == false) {
			
			return null;
		}
		maybeJob.get().setName(data.getName());
		maybeJob.get().setStartDate(data.getStartDate());
		maybeJob.get().setEndDate(data.getEndDate());
		maybeJob.get().setTemp(maybeTemp.get());
		this.repository.save(maybeJob.get());
		
		System.out.println(maybeJob.get().getName());
		return maybeJob.get(); 
	}
}

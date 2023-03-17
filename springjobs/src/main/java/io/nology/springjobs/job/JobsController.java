package io.nology.springjobs.job;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.springjobs.temp.Temp;
import io.nology.springjobs.temp.TempsCreateDTO;
import io.nology.springjobs.temp.TempsService;


@CrossOrigin
@RestController
@RequestMapping("/jobs")
public class JobsController {

	@Autowired
	private JobsService service;
	@Autowired
	private TempsService tempService;

	@GetMapping
	public ResponseEntity<List<Job>> all(){
		List<Job> allJobs = this.service.all();
		

//		System.out.println(allJobs.get(0).name);
//		System.out.println(allJobs.get(1).temp.getFirstName());
		
		return new ResponseEntity<>(allJobs, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Job> find(@PathVariable Long id){
//		System.out.println("test");
		Optional<Job> maybeJob = this.service.find(id);
		
		if(maybeJob.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(maybeJob.get(), HttpStatus.OK);
	}

	
	@GetMapping(value = "/assignedTemp/{id}/{assigned}")
	public ResponseEntity<List<Job>> assigned(@PathVariable Long id, @PathVariable Boolean assigned){
		
		List<Job> allValidJobs = this.service.tempAssigned(id, assigned);
		
		return new ResponseEntity<>(allValidJobs, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Job> create(@Valid @RequestBody JobsCreateDTO data){
		Job JobsCreated = this.service.create(data);
		return new ResponseEntity<>(JobsCreated, HttpStatus.CREATED);
	}
	
	//POST EXAMPLE
		//{
		//"name":"test",
		//"startDate": "1995-11-22 11:33:44",
		//"endDate": "1996-11-22 11:33:44",
		//"temp": 1
		//}	
	
	
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable long id){
		
//		this.service.changeTemp(service.find(id).get().getId(), service.find(id).get().getTemp().getId());
		
		Job deletedJob = this.service.delete(id);

		if(deletedJob == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(deletedJob, HttpStatus.OK);
		
	}
	
	@PatchMapping("/name/{id}")
	public ResponseEntity<?> changeName(@PathVariable long id){
		Job changedJob = this.service.changeName(id);
		if(changedJob == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(changedJob, HttpStatus.OK);
		
	}
	
	
	@PatchMapping("/changeTemp/{jobId}/{tempId}")
	public ResponseEntity<?> changeTemp(@PathVariable long jobId, @PathVariable long tempId){
		Job changedJob = this.service.changeTemp(jobId, tempId);
		if(changedJob == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(changedJob, HttpStatus.OK);
		
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateJob(@PathVariable long id, @Valid @RequestBody JobsCreateDTO data){
		Job changedJob = this.service.updateJob(id, data);
		if(changedJob == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(changedJob, HttpStatus.OK);
		
	}
	
	
	

}

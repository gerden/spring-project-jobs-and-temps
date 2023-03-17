package io.nology.springjobs.temp;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nology.springjobs.job.Job;



@CrossOrigin
@RestController
@RequestMapping("/temps")
public class TempsController {


	@Autowired
	private TempsService service;
	
	@PostMapping
	public ResponseEntity<Temp> create(@Valid @RequestBody TempsCreateDTO data){
		Temp TempsCreated = this.service.create(data);
		return new ResponseEntity<>(TempsCreated, HttpStatus.CREATED);
	}
	
//POST EXAMPLE
	
//		{
//		"firstName": "alex",
//		"lastName": "smith"
//		}
	
	@GetMapping
	public ResponseEntity<List<Temp>> all(){
		List<Temp> allTemps = this.service.all();
		return new ResponseEntity<>(allTemps, HttpStatus.OK);
	}
	
	@GetMapping(value = "/jobAvailable/{id}")
	public ResponseEntity<List<Temp>> jobAvailable(@PathVariable Long id){
		List<Temp> allTemps = this.service.availableTempsForJob(id);		
		
		return new ResponseEntity<>(allTemps, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Temp> find(@PathVariable Long id){
		Optional<Temp> maybeTemp = this.service.find(id);
		
		if(maybeTemp.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(maybeTemp.get(), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable long id){
		Temp deletedTemp = this.service.delete(id);
		if(deletedTemp == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(deletedTemp, HttpStatus.OK);
		
	}

}


/* 	
 * 1. basic post test with basic response
 * @PostMapping
	public ResponseEntity<?> create(){
		return new ResponseEntity<>("you posted a temps", HttpStatus.CREATED);
	}
 
 */

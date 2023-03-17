package io.nology.springjobs.job;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobsRepository extends JpaRepository<Job, Long>{
	
}

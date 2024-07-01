package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Cook;


public interface CookRepository extends CrudRepository<Cook,Long> {
	
	
	
	public List<Cook> findBySurname(String surname);

	public boolean existsByName(String name);
	
	public boolean existsBySurname(String surname);
	
	public boolean existsByBirth(LocalDate birth);


	
		



}



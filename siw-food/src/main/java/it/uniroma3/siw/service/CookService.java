package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.repository.CookRepository;


@Service
public class CookService {
	
	@Autowired 
	private CookRepository cookRepository;
	
	public Cook findById( Long id) {
		return cookRepository.findById(id).get();
		}
	
	public Iterable<Cook> findAll(){
		return cookRepository.findAll();
	}
	
	public void save(Cook c) {
		cookRepository.save(c);
	}
	

	public List<Cook> findBySurname(String surname) {
		return cookRepository.findBySurname(surname);
	}
	
	public void remove(Cook c) {
		cookRepository.delete(c);
	}

	public boolean existsByNameSurnameBirth(Cook c) {
		return cookRepository.existsByName(c.getName()) && cookRepository.existsBySurname(c.getSurname()) && 
				cookRepository.existsByBirth(c.getBirth());
	}
	
	
}

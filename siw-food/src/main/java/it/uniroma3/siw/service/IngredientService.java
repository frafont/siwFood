package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.repository.IngredientRepository;


@Service
public class IngredientService {

	
	@Autowired IngredientRepository ingredientRepository;

	public Iterable<Ingredient> findAll() {
		return this.ingredientRepository.findAll();
	}

	public void save(Ingredient ingredient) {
		this.ingredientRepository.save(ingredient);
	}
	
	public Ingredient findById(Long id) {
		return ingredientRepository.findById(id).get();
	}

	public boolean existsByName(String name) {
		return ingredientRepository.existsByName(name);
	}
}

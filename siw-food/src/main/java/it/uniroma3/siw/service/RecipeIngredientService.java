package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.RecipeIngredient;
import it.uniroma3.siw.repository.RecipeIngredientRepository;

@Service
public class RecipeIngredientService {
	
	@Autowired RecipeIngredientRepository recipeIngredientRepository;

	public Iterable<RecipeIngredient> findAll() {
		return this.recipeIngredientRepository.findAll();
	}

	public void save(RecipeIngredient ingredient) {
		this.recipeIngredientRepository.save(ingredient);
	}
	
	public RecipeIngredient findById(Long id) {
		return recipeIngredientRepository.findById(id).get();
	}
	
	public void remove(RecipeIngredient ing) {
		this.recipeIngredientRepository.delete(ing);
	}
	
	
}

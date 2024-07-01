package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.repository.RecipeRepository;

@Service
public class RecipeService {
	@Autowired 
	private RecipeRepository recipeRepository;
	
	public Recipe findById( Long id) {
		return recipeRepository.findById(id).get();
		}
	
	public Iterable<Recipe> findAll(){
		return recipeRepository.findAll();
	}
	
	public void save(Recipe r) {
		recipeRepository.save(r);
	}
	
	public void delete(Recipe recipe) {
		recipeRepository.delete(recipe);
	}

	public Recipe findByName(String name) {
		return recipeRepository.findByName(name);
	}
	
 
}

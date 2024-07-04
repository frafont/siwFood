package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe,Long> {
	
	public Recipe findByName(String name);
	
	@Query("SELECT r FROM Recipe r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))")
	public List<Recipe> findRecipes(String name);
	
	@Query("SELECT r FROM Recipe r WHERE LOWER(r.type) LIKE LOWER(CONCAT('%', :type, '%'))")
	public List<Recipe> findRecipesByType(String type);
	
	

}

package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.RecipeIngredient;
import it.uniroma3.siw.service.RecipeIngredientService;
import it.uniroma3.siw.service.RecipeService;

@Controller
public class RecipeIngredientController {

	@Autowired RecipeIngredientService recipeIngredientService;
	
	@Autowired RecipeService recipeService;
	
	
	@GetMapping("admin/removeIngredient/{idRecipe}/{idIngredient}")
	public String AdminremoveIngredient(@PathVariable("idRecipe")Long idRec,@PathVariable("idIngredient")Long idIng, Model model) {
		Recipe recipe=this.recipeService.findById(idRec);
		RecipeIngredient ingredient=this.recipeIngredientService.findById(idIng);
		recipe.getIngredients().remove(ingredient);
		this.recipeIngredientService.remove(ingredient);
		this.recipeService.save(recipe);
		model.addAttribute("recipe", recipe);
		return  "redirect:/admin/manageIngredients/"+recipe.getId();
	}
	
	@GetMapping("cookUser/removeIngredient/{idRecipe}/{idIngredient}")
	public String removeIngredient(@PathVariable("idRecipe")Long idRec,@PathVariable("idIngredient")Long idIng, Model model) {
		Recipe recipe=this.recipeService.findById(idRec);
		RecipeIngredient ingredient=this.recipeIngredientService.findById(idIng);
		recipe.getIngredients().remove(ingredient);
		this.recipeIngredientService.remove(ingredient);
		this.recipeService.save(recipe);
		model.addAttribute("recipe", recipe);
		return "redirect:/cookUser/manageIngredients/"+recipe.getId();
	}
	
	
	@PostMapping("cookUser/updateQuantity")
	public String updateRecipeIngredient(@RequestParam("recipeIngredientId")Long id,@RequestParam("quantity")Integer quantity,@RequestParam("unit") String unit,Model model) {
		RecipeIngredient ing=this.recipeIngredientService.findById(id);
		ing.setQuantity(quantity);
		ing.setUnit(unit);
		this.recipeIngredientService.save(ing);
		Recipe r=ing.getRecipe();
		model.addAttribute("recipe", r);
		return "redirect:/cookUser/manageIngredients/"+ r.getId();
		
	}
	
	@PostMapping("admin/updateQuantity")
	public String AdminupdateRecipeIngredient(@RequestParam("recipeIngredientId")Long id,@RequestParam("quantity")Integer quantity,@RequestParam("unit") String unit,Model model) {
		RecipeIngredient ing=this.recipeIngredientService.findById(id);
		ing.setQuantity(quantity);
		ing.setUnit(unit);
		this.recipeIngredientService.save(ing);
		Recipe r=ing.getRecipe();
		model.addAttribute("recipe", r);
		return "redirect:/admin/manageIngredients/"+r.getId();
		
	}
	
	
}

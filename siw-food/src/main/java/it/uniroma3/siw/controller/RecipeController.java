package it.uniroma3.siw.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import it.uniroma3.siw.service.CookService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.RecipeIngredientService;
import it.uniroma3.siw.service.RecipeService;
import it.uniroma3.siw.validator.RecipeValidator;
import jakarta.validation.Valid;
import jakarta.persistence.EntityManager;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.RecipeRepository;
import it.uniroma3.siw.model.RecipeIngredient;


@Controller
public class RecipeController {
	
	private static final String UPLOAD_DIR= "C:\\Users\\39345\\Desktop\\siwFood\\siw-food\\src\\main\\resources\\static\\images";
	
	@Autowired RecipeService recipeService;
	
	@Autowired RecipeRepository recipeRepository;
	
	@Autowired CookService cookService;
	
	@Autowired RecipeIngredientService recipeIngredientService;
	
	@Autowired GlobalController gc;
	
	@Autowired CredentialsService credentialsService;
	
	@Autowired RecipeValidator recipeValidator;
	
	@Autowired EntityManager entityManager;

	
	


	@GetMapping("/recipe/{id}")
	public String getRecipe(@PathVariable("id")Long id, Model model) {
		model.addAttribute("recipe",this.recipeService.findById(id));
		return "recipe.html";
	}
	
	@GetMapping("/recipe")
	  public String showRecipes(Model model) {
	    model.addAttribute("recipes", this.recipeService.findAll());
	    return "recipes.html";
	  }
	
	
	

	
	@PostMapping("/formSearchRecipe")
	public String getRecipeByName(@RequestParam String name, Model model) {
		List<Recipe> recipes=this.recipeRepository.findRecipes(name);
		model.addAttribute("recipes", recipes);
		return "recipes.html";
	}
	
	@PostMapping("/formSearchRecipeByType")
	public String getRecipeByType(@RequestParam String type,Model model) {
		List<Recipe> recipes=this.recipeRepository.findRecipesByType(type);
		model.addAttribute("recipes", recipes);
		return "recipes.html";
	}
	
	
	
	@PostMapping("/recipe")
	public String newRecipe(@Valid @ModelAttribute("recipe")Recipe recipe,@RequestParam("immagine") MultipartFile file, BindingResult recipeBindingResult) {
		UserDetails u=gc.getUser();
		String username=u.getUsername();
		Credentials credenziali=this.credentialsService.getCredentials(username);
		User utenteCorrente=credenziali.getUser();
		Cook cuocoCorrente= utenteCorrente.getCook();
		recipe.setCook(cuocoCorrente);
		recipe.ingredienti=new ArrayList<RecipeIngredient>();
		
		this.recipeValidator.validate(recipe, recipeBindingResult);
		if(!recipeBindingResult.hasErrors()) {
			if(!file.isEmpty()) {
				try {
					String fileName = StringUtils.cleanPath(file.getOriginalFilename());
					Path path = Paths.get(UPLOAD_DIR + File.separator + fileName);
					Files.write(path, file.getBytes());
					recipe.setUrlImage(fileName);
					cuocoCorrente.getRecipes().add(recipe);
					this.recipeService.save(recipe);
					return "redirect:recipe/" + recipe.getId();
			} catch (IOException e) {
				e.printStackTrace();
				return "addRecipe.html";
			}
		}
	}
			if(credenziali.getRole().equals("COOK"))
				return "/cookUser/addRecipe.html";
			else
				return "/admin/addRecipe.html";
		
	}
	
	@GetMapping("admin/manageRecipes")
	public String AdminManageRecipes(Model model) {
		model.addAttribute("recipes",this.recipeService.findAll());
		return "admin/manageRecipes.html";
	}
	
	@GetMapping("/admin/addRecipe")
	public String AdminformNewRecipe(Model model) {
		Recipe recipe=new Recipe();
		model.addAttribute("recipe",recipe);
		
		return "admin/addRecipe.html";
	}
	
	@GetMapping("/admin/addRecipeToCook/{idCook}")
	public String addRecipeToCook(@PathVariable("idCook") Long idCook,Model model) {
		Cook c=this.cookService.findById(idCook);
		model.addAttribute("cook", c);
		Recipe recipe=new Recipe();
		model.addAttribute("recipe",recipe);
		return "admin/addRecipeToCook.html";
	}
	
	@PostMapping("/admin/addRecipeToCook")
	public String setRecipeToCook(@Valid @ModelAttribute("recipe")Recipe recipe, BindingResult recipeBindingResult,@RequestParam("cookId")Long cookId,@RequestParam("immagine") MultipartFile file) {
		Cook c= this.cookService.findById(cookId);
		recipe.setCook(c);
		recipe.ingredienti=new ArrayList<RecipeIngredient>();
		
		this.recipeValidator.validate(recipe, recipeBindingResult);
		if(!recipeBindingResult.hasErrors()) {
			if(!file.isEmpty()) {
				try {
					String fileName = StringUtils.cleanPath(file.getOriginalFilename());
					Path path = Paths.get(UPLOAD_DIR + File.separator + fileName);
					Files.write(path, file.getBytes());
					recipe.setUrlImage(fileName);
					c.getRecipes().add(recipe);
					this.recipeService.save(recipe);
					return "redirect:/cook/" + c.getId();
					
			} catch (IOException e) {
				e.printStackTrace();
				return "redirect:/admin/addRecipeToCook/"+c.getId();
			}
		}
	}

	return "redirect:/admin/addRecipeToCook/" +c.getId();
}
	
	
	
	
	@GetMapping("admin/removeRecipe/{id}")
	public String AdminremoveRecipe(@PathVariable("id")Long id, Model model) {
		Recipe recipe=this.recipeService.findById(id);
		Cook c=recipe.cuoco;
		if(c!=null) {
			c.getRecipes().remove(recipe);
		}
		if(recipe.getIngredients().isEmpty()) {
				this.recipeService.delete(recipe);
				return "redirect:/";
		}
		else {
				for (RecipeIngredient ing: recipe.getIngredients()) {
					ing.setRecipe(null);
					this.recipeIngredientService.remove(ing);
				}
				recipe.getIngredients().removeAll(recipe.getIngredients());
				
		}
		
		this.recipeService.delete(recipe);
		return "redirect:/";
	
		
	}
	
	@GetMapping("/cookUser/manageRecipes")
	public String manageRecipes(Model model) {
		UserDetails u=gc.getUser();
		String username=u.getUsername();
		Credentials credenziali=this.credentialsService.getCredentials(username);
		User utenteCorrente=credenziali.getUser();
		Cook cuocoCorrente= utenteCorrente.getCook();
		model.addAttribute("recipes", cuocoCorrente.getRecipes());
		return "cookUser/manageRecipes.html";
	}
	
	@GetMapping("/cookUser/addRecipe")
	public String formNewRecipe(Model model) {
		Recipe recipe=new Recipe();
		model.addAttribute("recipe",recipe);
		
		return "cookUser/addRecipe.html";
	}
	
	
	
	@GetMapping("cookUser/removeRecipe/{id}")
	public String removeRecipe(@PathVariable("id")Long id, Model model) {
		Recipe recipe=this.recipeService.findById(id);
		Cook c=recipe.cuoco;
		if(c!=null) {
			c.getRecipes().remove(recipe);
		}
		if(recipe.getIngredients().isEmpty()) {
				this.recipeService.delete(recipe);
				return "redirect:/";
		}
		else {
				for (RecipeIngredient ing: recipe.getIngredients()) {
					ing.setRecipe(null);
					this.recipeIngredientService.remove(ing);
				}
				recipe.getIngredients().removeAll(recipe.getIngredients());
				
		}
		
		this.recipeService.delete(recipe);
		return "redirect:/";
	}
	
	
	
	
}

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
import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.RecipeIngredient;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.IngredientRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.IngredientService;
import it.uniroma3.siw.service.RecipeIngredientService;
import it.uniroma3.siw.service.RecipeService;
import it.uniroma3.siw.validator.IngredientValidator;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;

@Controller
public class IngredientController {

	private static final String UPLOAD_DIR= "C:\\Users\\39345\\Desktop\\siwFood\\siw-food\\src\\main\\resources\\static\\images";
	
	@Autowired
	IngredientService ingredientService;
	
	@Autowired
	IngredientRepository ingredientRepository;

	@Autowired
	RecipeService recipeService;

	@Autowired
	RecipeIngredientService recipeIngredientService;

	@Autowired
	IngredientValidator ingredientValidator;

	@Autowired
	GlobalController gc;

	@Autowired
	CredentialsService credentialsService;

	@Autowired
	EntityManager entityManager;

	@PostMapping("/formSearchIngredient")
	public String getRecipeByName(@RequestParam String name, Model model) {
	List<Ingredient> ingredients = this.ingredientRepository.findIngredients(name);
		model.addAttribute("ingredients", ingredients);
		return "ingredients.html";
	}

	@GetMapping("/admin/formNewIngredient")
	public String AdminformNewIngredient(Model model) {
		Ingredient ingredient = new Ingredient();
		model.addAttribute("ingredient", ingredient);

		return "admin/formNewIngredient.html";
	}

	@GetMapping("/cookUser/formNewIngredient")
	public String formNewIngredient(Model model) {
		Ingredient ingredient = new Ingredient();
		model.addAttribute("ingredient", ingredient);

		return "cookUser/formNewIngredient.html";
	}

	@PostMapping("/ingredient")
	public String newIngredient(@Valid @ModelAttribute("ingredient") Ingredient ingredient,
			@RequestParam("immagine") MultipartFile file, BindingResult ingredientBindingResult) {
		this.ingredientValidator.validate(ingredient, ingredientBindingResult);

		if (!ingredientBindingResult.hasErrors()) {
			if (!file.isEmpty()) {
				try {
					String fileName = StringUtils.cleanPath(file.getOriginalFilename());
					Path path = Paths.get(UPLOAD_DIR + File.separator + fileName);
					Files.write(path, file.getBytes());
					ingredient.setUrlImage(fileName);
					this.ingredientService.save(ingredient);
					return "redirect:/ingredient";
				} catch (IOException e) {
					e.printStackTrace();
					return "formNewIngredient.html";
				}
			}

		}
		UserDetails u = gc.getUser();
		String username = u.getUsername();
		Credentials credenziali = this.credentialsService.getCredentials(username);
		if (credenziali.getRole().equals("COOK"))
			return "/cookUser/formNewIngredient.html";
		else
			return "/admin/formNewIngredient.html";

	}

	@GetMapping("/ingredient")
	public String showIngredients(Model model) {
		model.addAttribute("ingredients", this.ingredientService.findAll());
		UserDetails u = gc.getUser();
		String username = u.getUsername();
		Credentials credenziali = this.credentialsService.getCredentials(username);
		if (credenziali.getRole().equals("COOK"))
			return "/cookUser/ingredients.html";
		else
			return "/admin/ingredients.html";
	}

	@GetMapping("admin/manageIngredients/{id}")
	public String AdminmanageIngredients(@PathVariable("id") Long id, Model model) {
		Recipe recipe = this.recipeService.findById(id);
		model.addAttribute("recipe", recipe);
		List<Ingredient> ingredientiNonPresenti = new ArrayList<Ingredient>();
		for (Ingredient ing : this.ingredientService.findAll()) {
			boolean presente = false;
			for (RecipeIngredient rIng : recipe.getIngredients()) {
				if (rIng.getIngredient().equals(ing)) {
					presente = true;
				}
			}
			if (!presente) {
				ingredientiNonPresenti.add(ing);
			}
		}
		model.addAttribute("ingredients", ingredientiNonPresenti);
		return "admin/manageIngredients.html";
	}

	@GetMapping("cookUser/manageIngredients/{id}")
	public String manageIngredients(@PathVariable("id") Long id, Model model) {
		Recipe recipe = this.recipeService.findById(id);
		UserDetails user=gc.getUser();
		String username=user.getUsername();
		Credentials credenziali= this.credentialsService.getCredentials(username);
		User utenteCorrente= credenziali.getUser();
		Cook cuocoCorrente= utenteCorrente.getCook();
		if(recipe.getCook()!=cuocoCorrente) {
			return "redirect:/cookUser/manageRecipes";
		}
		model.addAttribute("recipe", recipe);
		List<Ingredient> ingredientiNonPresenti = new ArrayList<Ingredient>();
		for (Ingredient ing : this.ingredientService.findAll()) {
			boolean presente = false;
			for (RecipeIngredient rIng : recipe.getIngredients()) {
				if (rIng.getIngredient().equals(ing)) {
					presente = true;
				}
			}
			if (!presente) {
				ingredientiNonPresenti.add(ing);
			}
		}
		model.addAttribute("ingredients", ingredientiNonPresenti);
		return "cookUser/manageIngredients.html";
	}

	@GetMapping("admin/setIngredientToRecipe/{idRecipe}/{idIngredient}")
	public String AdminsetIngredientToRecipe(Model model, @PathVariable("idRecipe") Long idRec,
			@PathVariable("idIngredient") Long idIng) {
		Ingredient ingredient = this.ingredientService.findById(idIng);
		Recipe recipe = this.recipeService.findById(idRec);

		RecipeIngredient ing = new RecipeIngredient();
		ing.setIngredient(ingredient);
		ing.setRecipe(recipe);
		this.recipeIngredientService.save(ing);
		recipe.getIngredients().add(ing);
		this.recipeService.save(recipe);
		model.addAttribute("recipe", recipe);
		return "redirect:/admin/manageIngredients/" + recipe.getId();
	}

	@GetMapping("cookUser/setIngredientToRecipe/{idRecipe}/{idIngredient}")
	public String setIngredientToRecipe(Model model, @PathVariable("idRecipe") Long idRec,
			@PathVariable("idIngredient") Long idIng) {
		Ingredient ingredient = this.ingredientService.findById(idIng);
		Recipe recipe = this.recipeService.findById(idRec);

		RecipeIngredient ing = new RecipeIngredient();
		ing.setIngredient(ingredient);
		ing.setRecipe(recipe);
		this.recipeIngredientService.save(ing);
		recipe.getIngredients().add(ing);
		this.recipeService.save(recipe);
		model.addAttribute("recipe", recipe);
		return "redirect:/cookUser/manageIngredients/" + recipe.getId();
	}
}

package it.uniroma3.siw.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CookRepository;
import it.uniroma3.siw.service.CookService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.validator.CookValidator;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;



@Controller
public class CookController {
	
	private static final String UPLOAD_DIR= "C:\\Users\\39345\\Desktop\\siwFood\\siw-food\\src\\main\\resources\\static\\images";
	
	@Autowired CookService cookService;
	
	@Autowired CookRepository cookRepository;
	
	@Autowired CookValidator cookValidator;
	
	@Autowired EntityManager entityManager;
	
	@Autowired UserService userService;
	
	@Autowired GlobalController gc;
	
	@Autowired CredentialsService credentialsService;
	
	
	@GetMapping("/cook/{id}")
	public String getCook(@PathVariable("id")Long id, Model model) {
		model.addAttribute("cook",this.cookService.findById(id));
		return "cook.html";
	}
	
	@GetMapping("/cook")
	  public String showCooks(Model model) {
	     model.addAttribute("cooks", this.cookService.findAll());
	    return "cooks.html";
	  }
	@GetMapping("/cook/recipes/{id}")
	public String getRecipesByCookId(@PathVariable("id") Long id, Model model) {
		Cook c=this.cookService.findById(id);
		model.addAttribute("recipes", c.getRecipes());
		return "recipes.html";
	}
	
	@GetMapping("admin/manageCooks")
	public String manageCook(Model model) {
		UserDetails u=gc.getUser();
		String username=u.getUsername();
		Credentials credenziali=this.credentialsService.getCredentials(username);
		User utenteCorrente=credenziali.getUser();
		Cook cuocoCorrente= utenteCorrente.getCook();
		List<Cook> cooks=(List<Cook>) this.cookService.findAll();
		cooks.remove(cuocoCorrente);
		model.addAttribute("cooks",cooks); //passa tutti i cuochi tranne te stesso
		return "admin/manageCooks.html";
	}
	
	@GetMapping("/admin/removeCook/{id}")
	public String removeCook(@PathVariable("id") Long id) {
		Cook c=this.cookService.findById(id);
		User utente= this.userService.findByNameAndSurname(c.getName(),c.getSurname());
		if(!c.getRecipes().isEmpty()) {
			for(Recipe r: c.getRecipes()) {
				r.setCook(null);
			}
		}
		if(utente!=null) {
			Credentials credenziali=this.credentialsService.findByUserId(utente.getId());
			this.userService.remove(utente);
			this.credentialsService.remove(credenziali);
			
		}
		
		this.cookService.remove(c);
		return "redirect:/admin/manageCooks";
	}
	
	@GetMapping("/cookUser/index")
	public String index() {
		return "cookUser/index.html";
	}
	
	@GetMapping ("/admin/formNewCook")
	public String formNewCook(Model model) {
		Cook cuoco= new Cook();
		model.addAttribute("cook",cuoco);
		return "admin/formNewCook.html";
	}
	
	@PostMapping("/cook")
	public String newCook(@Valid @ModelAttribute("cook")Cook cook,@RequestParam("immagine")MultipartFile file ,BindingResult cookBindingResult) {
		this.cookValidator.validate(cook, cookBindingResult);
		
		if(!cookBindingResult.hasErrors()) {
			if(!file.isEmpty())
				try {
					String fileName = StringUtils.cleanPath(file.getOriginalFilename());
					Path path = Paths.get(UPLOAD_DIR + File.separator + fileName);
					Files.write(path, file.getBytes());
					cook.setUrlImage(fileName);
					this.cookService.save(cook);
					return "redirect:cook/" + cook.getId();
				} catch (IOException e) {
					e.printStackTrace();
					return "formNewCook.html";
				}
		
		}
	
			return "admin/formNewCook.html";
}
		
	
	
	
	
	@PostMapping("/formSearchCooks")
	public String getCookBySurname(@RequestParam String surname, Model model) {
		List<Cook> cooks=this.cookRepository.findCooks(surname);
		model.addAttribute("cooks", cooks);
		return "cooks.html";
	}
	
	
}

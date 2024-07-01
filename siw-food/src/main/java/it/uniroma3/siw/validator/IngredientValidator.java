package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.service.IngredientService;

@Component
public class IngredientValidator implements Validator {
	
	@Autowired IngredientService ingredientService;

	@Override
	public boolean supports(Class<?> aClass) {
		return Credentials.class.equals(aClass);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		Ingredient i=(Ingredient) target;
		if(this.ingredientService.existsByName(i.getName())) {
			errors.reject("ingredient.duplicate");
		}
		
	}

	
}

package it.uniroma3.siw.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Recipe;

@Component
public class RecipeValidator implements Validator {
	


	@Override
	public boolean supports(Class<?> aClass) {
		return Credentials.class.equals(aClass);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
			Recipe r=(Recipe) target;
			Cook c=r.getCook();
			for (Recipe recipe : c.getRecipes()) {
				if(r.getName().equals(recipe.getName())) {
					errors.reject("recipe.duplicate");
				}
			}
			
			if(!r.getType().equals("antipasto") && !r.getType().equals("primo") &&
					!r.getType().equals("secondo") && !r.getType().equals("dolce")) {
				errors.reject("recipe.invalid");
			}
			
			
		
		
		
		}
		
}

	


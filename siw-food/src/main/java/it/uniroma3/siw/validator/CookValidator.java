package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.CookService;

@Component
public class CookValidator implements Validator {
	
	@Autowired CookService cookService;

	@Override
	public boolean supports(Class<?> aClass) {
		return Credentials.class.equals(aClass);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
			Cook c=(Cook) target;
			
			if(c.getName()!=null && c.getSurname()!=null && c.getBirth()!=null 
					&& cookService.existsByNameSurnameBirth(c)) {
				errors.reject("cook.duplicate");
			}
			if(c.getBirth().getYear()<1900 || c.getBirth().getYear()>2024) {
				errors.reject("birth.error");
			}
		
		
		
		}
		
}

	


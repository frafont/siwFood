package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.UserService;



@Component
public class UserValidator implements Validator {

	@Autowired UserService userService;
	
	@Override
	public void validate(Object o, Errors errors) {
		User u=(User) o;
		if(u.getBirth().getYear()<1900 || u.getBirth().getYear()>2024) {
			errors.reject("birth.error");
		}
	}
	@Override
	public boolean supports(Class<?> aClass) {
		return Credentials.class.equals(aClass);
	}

	


}

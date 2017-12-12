package com.springboot.study.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springboot.study.model.Customer;


@Component("beforeCreateCustomerRepository")
public class CustomerValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
//    	System.out.println("Inside support method of Validator");
        return Customer.class.equals(clazz);
    }
 
    @Override
    public void validate(Object obj, Errors errors) {
    	
//    	System.out.println("Inside validator method of Validator");
    	Customer user = (Customer) obj;
        if (checkInputString(user.getFirstName())) {
            errors.rejectValue("firstName", "", "First name cannot be empty");
        }
    
        if (checkInputString(user.getLastName())) {
            errors.rejectValue("lastName", "", "Last name cannot be empty");
        }
    }
 
    private boolean checkInputString(String input) {
        return (input == null || input.trim().length() == 0);
    }
}


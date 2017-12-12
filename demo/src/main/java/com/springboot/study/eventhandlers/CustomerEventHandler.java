package com.springboot.study.eventhandlers;

import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import com.springboot.study.model.Customer;

@RepositoryEventHandler(Customer.class)
public class CustomerEventHandler {

	@HandleBeforeCreate
	public void handleBeforeSave(Customer c) {
		System.out.println("Before create event handler triggered");
	}
	
	@HandleAfterDelete 
	public void handleAfterDelete(Customer c) {
		System.out.println("After delete event handler triggered");
	}

}

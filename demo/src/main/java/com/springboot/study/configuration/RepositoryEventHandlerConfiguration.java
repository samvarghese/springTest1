package com.springboot.study.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springboot.study.eventhandlers.CustomerEventHandler;

@Configuration
public class RepositoryEventHandlerConfiguration {
	
	@Bean
	CustomerEventHandler customerEventHandler(){
		return new CustomerEventHandler();
	}
}

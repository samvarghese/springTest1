package com.springboot.study.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterRegistrationConfiguration {

//	@Bean
//	public FilterRegistrationBean myFilter() {
//		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//		registrationBean.setFilter(new MyFilter());
//		registrationBean.addUrlPatterns("/customer/*");
//
//		return registrationBean;
//	}

//	@Bean
//	public Filter compressFilter() {
//	    CompressingFilter compressFilter = new CompressingFilter();
//	    return compressFilter;
//	}

}

//There are three ways to add your filter,
//
//Annotate your filter with one of the Spring stereotypes such as @Component
//Register a @Bean with Filter type in Spring @Configuration
//Register a @Bean with FilterRegistrationBean type in Spring @Configuration
//
//Either #1 or #2 will do if you want your filter applies to all requests without customization, use #3 otherwise. You don't need to specify component scan for #1 to work as long as you place your filter class in the same or sub-package of your SpringApplication class. For #3, use along with #2 is only necessary when you want Spring to manage your filter class such as have it auto wired dependencies. It works just fine for me to new my filter which doesn't need any dependency autowiring/injection.
//
//Although combining #2 and #3 works fine, I was surprised it doesn't end up with two filters applying twice. My guess is that Spring combines the two beans as one when it calls the same method to create both of them. In case you want to use #3 alone with authowiring, you can AutowireCapableBeanFactory
package com.springboot.study.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.study.model.ApplicationUser;
import com.springboot.study.repository.ApplicationUserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/users")
public class UserController {

    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public HashMap<String, Object> signUp(@RequestBody ApplicationUser user) {
    	
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        
        applicationUserRepository.save(user);
        
        HashMap<String, Object> returnValue = new HashMap<String, Object>();
        returnValue.put("msg", "User successfully created");
        return returnValue;
    }
    
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody HashMap<String, Object> requestBody) {
    	
    	String username = (String) requestBody.get("username");
    	String password = (String) requestBody.get("password");
    	
    	HashMap<String, Object> returnValue = new HashMap<String, Object>();
    	ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
    	
    	if((applicationUser != null) && (bCryptPasswordEncoder.matches(password, applicationUser.getPassword()))){
    		    		
    		try {
				String jwt = Jwts.builder()
								  .setSubject("users/" + applicationUser.getId())
//    				  			.setExpiration(new Date(1300819380))
								  .claim("name", applicationUser.getUsername())
								  .signWith(
								    SignatureAlgorithm.HS256,
								    "my super secret key".getBytes("UTF-8")
								  )
								  .compact();
				
				returnValue.put("loginId", username);
				returnValue.put("password", password);
				returnValue.put("token", "Bearer " + jwt);
				returnValue.put("messageId", 200);
				
				for (String name: returnValue.keySet()){

		            String key =name.toString();
		            String value = returnValue.get(name).toString();  
		            System.out.println(key + " " + value);  

				} 
				
				return new ResponseEntity<Object>(returnValue, HttpStatus.OK);

			} catch (UnsupportedEncodingException e) {
				
				returnValue.put("message", "Token creation Error");
				returnValue.put("error", e.toString());
				return new ResponseEntity<Object>(returnValue, HttpStatus.INTERNAL_SERVER_ERROR);
			}
    		
    	}else{
    		
    		returnValue.put("message", "Wrong Credentials / User not found");
    		return new ResponseEntity<Object>(returnValue, HttpStatus.BAD_REQUEST);
    	}
        
    }
}

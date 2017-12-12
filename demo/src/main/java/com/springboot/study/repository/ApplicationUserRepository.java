package com.springboot.study.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.springboot.study.model.ApplicationUser;

public interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUser, Long>{
	ApplicationUser findByUsername(String username);
}

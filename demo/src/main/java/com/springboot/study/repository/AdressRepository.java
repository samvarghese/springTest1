package com.springboot.study.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.springboot.study.model.Adress;

@RepositoryRestResource(collectionResourceRel = "adress", path = "adress")
public interface AdressRepository extends PagingAndSortingRepository<Adress, Long>{

}

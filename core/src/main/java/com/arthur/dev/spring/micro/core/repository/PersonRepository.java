package com.arthur.dev.spring.micro.core.repository;


import com.arthur.dev.spring.micro.core.model.Person;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
}

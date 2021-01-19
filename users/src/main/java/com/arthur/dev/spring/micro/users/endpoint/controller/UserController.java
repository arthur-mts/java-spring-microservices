package com.arthur.dev.spring.micro.persons.endpoint.controller;

import com.arthur.dev.spring.micro.core.model.Person;
import com.arthur.dev.spring.micro.persons.endpoint.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {
    private final PersonService personService;

    @PostMapping
    public void signUp(@RequestBody Person person){
        person.set
    }


    @GetMapping
    public ResponseEntity<Iterable<Person>> list(Pageable pageable) {
        return new ResponseEntity<Iterable<Person>>(personService.list(pageable), HttpStatus.OK);
    }
}

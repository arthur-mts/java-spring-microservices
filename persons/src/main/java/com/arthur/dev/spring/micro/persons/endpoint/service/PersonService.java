package com.arthur.dev.spring.micro.persons.endpoint.service;


import com.arthur.dev.spring.micro.core.model.Person;
import com.arthur.dev.spring.micro.core.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {
    private final PersonRepository personRepository;

    public Iterable<Person> list(Pageable pageable) {
        log.info(pageable.toString());
        return personRepository.findAll(pageable);
    }

}

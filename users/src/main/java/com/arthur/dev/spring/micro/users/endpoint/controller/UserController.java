package com.arthur.dev.spring.micro.users.endpoint.controller;

import com.arthur.dev.spring.micro.core.model.User;
import com.arthur.dev.spring.micro.users.endpoint.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    
    @PostMapping
    public void signUp(@RequestBody User user){
        userService.save(user);
    }


    @GetMapping
    public ResponseEntity<Iterable<User>> list(Pageable pageable) {
        return new ResponseEntity<Iterable<User>>(userService.list(pageable), HttpStatus.OK);
    }
}

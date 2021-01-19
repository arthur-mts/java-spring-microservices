package com.arthur.dev.spring.micro.users.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class UserAlredyExistsException extends HttpClientErrorException {
    public UserAlredyExistsException(String email) {
        super(HttpStatus.CONFLICT, "Um usuário já existe com esse email: "+email);
    }
}

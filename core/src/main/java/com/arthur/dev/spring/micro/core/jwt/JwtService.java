package com.arthur.dev.spring.micro.core.jwt;

import com.arthur.dev.spring.micro.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class JwtService {

    private UserRepository userRepository;



}

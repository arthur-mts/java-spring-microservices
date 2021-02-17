package com.arthur.dev.spring.micro.users.endpoint.service;


import com.arthur.dev.spring.micro.core.model.User;
import com.arthur.dev.spring.micro.core.repository.UserRepository;
import com.arthur.dev.spring.micro.users.exceptions.UserAlredyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository userRepository;

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Iterable<User> list(Pageable pageable) {
        log.info(pageable.toString());
        return userRepository.findAll(pageable);
    }

    public void save(User user){
        User userAlredyExists = userRepository.findByEmail(user.getEmail());
        if(userAlredyExists != null) {
            throw new UserAlredyExistsException(user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


}

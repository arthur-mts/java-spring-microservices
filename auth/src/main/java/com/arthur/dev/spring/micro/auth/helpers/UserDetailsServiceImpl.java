package com.arthur.dev.spring.micro.auth.helpers;

import com.arthur.dev.spring.micro.core.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.arthur.dev.spring.micro.core.repository.UserRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User byEmail = userRepository.findByEmail(email);
        if(byEmail == null) {
            throw new UsernameNotFoundException("User with email '"+email+"' not found");
        }

        UserDetailsImp userDetail = UserDetailsImp.builder().
                email(byEmail.getEmail()).
                password(byEmail.getPassword())
                .build();

        log.info("USER FOUNDED!");
        return userDetail;
    }
}

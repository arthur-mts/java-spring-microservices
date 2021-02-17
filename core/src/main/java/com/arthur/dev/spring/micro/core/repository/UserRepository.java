package com.arthur.dev.spring.micro.core.repository;

import com.arthur.dev.spring.micro.core.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByEmail(String email);
}

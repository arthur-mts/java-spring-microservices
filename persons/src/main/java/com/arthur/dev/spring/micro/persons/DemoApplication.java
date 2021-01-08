package com.arthur.dev.spring.micro.persons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//As anotações das linhas 10-11 servem para indicar onde as entities
//e os repos da aplicação estão, para que o spring boot
//possa ligalos ao controller e ao service.
@SpringBootApplication
@EntityScan({"com.arthur.dev.spring.micro.core.model"})
@EnableJpaRepositories({"com.arthur.dev.spring.micro.core.repository"})
@EnableEurekaClient
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

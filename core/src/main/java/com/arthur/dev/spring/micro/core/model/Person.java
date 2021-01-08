package com.arthur.dev.spring.micro.core.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Person implements AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull(message = "column 'first name' is required")
    @Column(nullable = false)
    private String firstName;

    @NotNull(message = "column 'last name' is required")
    @Column(nullable = false)
    private String lastName;
}

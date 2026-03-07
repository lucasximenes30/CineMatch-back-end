package com.cinematch.lucasximenes30.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;


@Data
@Entity
@Table(name = "usuario")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "password")
    private String password;
}

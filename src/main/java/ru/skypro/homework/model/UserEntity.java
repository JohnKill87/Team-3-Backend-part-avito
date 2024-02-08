package ru.skypro.homework.model;

import ru.skypro.homework.dto.Role;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image;
}

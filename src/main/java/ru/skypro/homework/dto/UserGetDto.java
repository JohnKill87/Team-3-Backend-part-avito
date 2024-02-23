package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class UserGetDto {
    private Long id;
    private String email;
    private String lastName;
    private String firstName;
    private String phone;
    private Role role;
    private String image;
}

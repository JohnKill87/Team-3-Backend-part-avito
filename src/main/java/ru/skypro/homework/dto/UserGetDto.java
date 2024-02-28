package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "User")
public class UserGetDto {

    private Integer id;
    private String email;
    private String lastName;
    private String firstName;
    private String phone;
    private Role role;
    private String image;
}

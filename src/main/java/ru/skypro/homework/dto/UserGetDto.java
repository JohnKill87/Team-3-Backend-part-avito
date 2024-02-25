package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.AvatarEntity;

@Data
public class UserGetDto {
    private int id;
    private String email;
    private String lastName;
    private String firstName;
    private String phone;
    private Role role;
    private AvatarEntity image;
}

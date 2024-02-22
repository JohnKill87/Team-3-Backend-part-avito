package ru.skypro.homework.dto;

import lombok.Data;
import lombok.Setter;

@Data
public class NewPassword {
    private String currentPassword;

    private String newPassword;

}

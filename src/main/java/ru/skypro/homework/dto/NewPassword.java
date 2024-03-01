package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.Size;

@Data
public class NewPassword {

    private String currentPassword;
    private String newPassword;
}


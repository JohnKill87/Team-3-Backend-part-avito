package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Register;

import java.util.Optional;

public interface AuthService {
    boolean login(String userName, String password);

    Optional<String> changePassword(String name, String currentPassword, String newPassword);
    boolean register(Register register);
}

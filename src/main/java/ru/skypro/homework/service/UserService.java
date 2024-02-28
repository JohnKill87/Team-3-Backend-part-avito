package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.dto.UserGetDto;
import ru.skypro.homework.model.UserEntity;

import java.io.IOException;
import java.util.Optional;

public interface UserService {
    User updateUserEntity(User user, Authentication authentication);
    void changePassword(NewPassword newPass, Authentication authentication);
    UserGetDto getUserInfo(Authentication authentication);
    void updateUserAvatar(MultipartFile image, Authentication authentication);
    boolean existsByEmail(String username);
    UserEntity getUser(Integer id);
    void createUser(Register register);
    byte[] getAvatar(Integer id);
    UserEntity getUserByEmail(String userName);
}

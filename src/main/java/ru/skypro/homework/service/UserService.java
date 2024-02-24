package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.dto.UserGetDto;
import ru.skypro.homework.model.UserEntity;

public interface UserService {
    User updateUserEntity(User user, Authentication authentication);
    void changePassword(NewPassword newPass, Authentication authentication);
    UserGetDto getUserInfo(Authentication authentication);

}

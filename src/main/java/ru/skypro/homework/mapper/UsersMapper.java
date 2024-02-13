package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.model.UserEntity;

@Service
public class UsersMapper {
    //    из Entity в DTO
    public User mapToUserDTO(UserEntity userEntity) {
        User user = new User();
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setPhone(userEntity.getPhone());
        return user;
    }

    //    из DTO в Entity
    public UserEntity mapToUserEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setPhone(user.getPhone());
        return userEntity;
    }
}

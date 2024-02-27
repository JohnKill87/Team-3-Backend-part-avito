package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.dto.UserGetDto;
import ru.skypro.homework.model.UserEntity;

@Component
public class UsersMapper {
    @Value("${path.to.default.user.photo}")
    private String photoPath;

    public UserEntity toEntity(Register register) {
        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(register.getUsername());
        userEntity.setFirstName(register.getFirstName());
        userEntity.setLastName(register.getLastName());
        userEntity.setPhone(register.getPhone());
        userEntity.setRole(register.getRole());
        userEntity.setAvatar(photoPath);

        return userEntity;
    }
    public static UserEntity mapFromRegisterToUserEntity(Register register) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(register.getUsername());
        userEntity.setRole(register.getRole());
        userEntity.setPhone(register.getPhone());
        userEntity.setFirstName(register.getFirstName());
        userEntity.setLastName(register.getLastName());
        userEntity.setPassword(register.getPassword());
        return userEntity;
    }

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
// из Entity в GetDto
    public UserGetDto mapToUserGetDto(UserEntity userEntity) {
        UserGetDto userGetDto = new UserGetDto();
        userGetDto.setId(userEntity.getId());
        userGetDto.setEmail(userEntity.getEmail());
        userGetDto.setFirstName(userEntity.getFirstName());
        userGetDto.setLastName(userEntity.getLastName());
        userGetDto.setImage(userEntity.getAvatar());
        userGetDto.setRole(userEntity.getRole());
        userGetDto.setPhone(userEntity.getPhone());
        return userGetDto;
    }

    //Из UserGetDto в UserEntity
    public UserEntity mapToUserEntity (UserGetDto userGetDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userGetDto.getId());
        userEntity.setEmail(userGetDto.getEmail());
        userEntity.setFirstName(userGetDto.getFirstName());
        userEntity.setLastName(userGetDto.getLastName());
        userEntity.setPhone(userGetDto.getPhone());
        userEntity.setRole(userGetDto.getRole());
        userEntity.setAvatar(userGetDto.getImage());
        return userEntity;
    }
}

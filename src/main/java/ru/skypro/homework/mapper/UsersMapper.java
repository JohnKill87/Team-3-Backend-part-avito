package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.dto.UserGetDto;
import ru.skypro.homework.model.UserEntity;

import java.util.Optional;

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
    public UserGetDto mapToUserGetDto(UserEntity userEntity) {
        UserGetDto userGetDto = new UserGetDto();
        userGetDto.setId(userEntity.getId());
        userGetDto.setEmail(userEntity.getEmail());
        userGetDto.setFirstName(userEntity.getFirstName());
        userGetDto.setLastName(userEntity.getLastName());
        userGetDto.setRole(userEntity.getRole());
        userGetDto.setPhone(userEntity.getPhone());
        userGetDto.setImage("/users/" + userEntity.getId() + "/avatar");
        return userGetDto;
    }
}

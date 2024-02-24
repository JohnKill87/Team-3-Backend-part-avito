package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.dto.UserGetDto;
import ru.skypro.homework.exception.PasswordIsNotMatchException;
import ru.skypro.homework.mapper.UsersMapper;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UsersMapper usersMapper;
    private final PasswordEncoder encoder;
    public UserServiceImpl(UserRepository userRepository, UsersMapper usersMapper, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.usersMapper = usersMapper;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void changePassword(NewPassword newPass, Authentication authentication) {
        String oldPassword = newPass.getCurrentPassword();
        String encodeNewPassword = encoder.encode(newPass.getNewPassword());
        Optional<UserEntity> userOptional = userRepository.findByEmail(authentication.getName());
        UserEntity userEntity = userOptional.get();
        if (!encoder.matches(oldPassword, userEntity.getPassword()))
            throw new PasswordIsNotMatchException();
        else{
            userEntity.setPassword(encodeNewPassword);
            userRepository.save(userEntity);
        }
    }
    @Override
    public User updateUserEntity(User user, Authentication authentication) {
        UserEntity userEntity = userRepository.findUserByEmail(authentication.getName());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setPhone(user.getPhone());
        userRepository.save(userEntity);
        return user;
    }

    @Override
    public UserGetDto getUserInfo(Authentication authentication) {
        UserEntity user = userRepository.findUserByEmail(authentication.getName());
        return usersMapper.mapToUserGetDto(user);
    }
}

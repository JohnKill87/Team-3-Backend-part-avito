package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.mapper.UsersMapper;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    public AuthServiceImpl(UserDetailsManager manager,
                           PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.manager = manager;
        this.encoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public Optional<String> changePassword(String username, String currentPassword, String newPassword) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(username);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            if (encoder.matches(currentPassword, user.getPassword())) {
                String newPasswordEncoded = encoder.encode(newPassword);
                user.setPassword(newPasswordEncoded);
                userRepository.save(user);
                return Optional.of("Password changed successfully");
            } else {
                return Optional.of("Current password is incorrect");
            }
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean register(Register register) {
        UserEntity user = UsersMapper.mapFromRegisterToUserEntity(register);
        if (manager.userExists(register.getUsername())) {
            return false;
        }

        user.setFirstName(register.getFirstName());
        user.setLastName(register.getLastName());
        user.setPhone(register.getPhone());
        user.setPassword(encoder.encode(register.getPassword()));
        user.setRole(register.getRole());
        user.setEmail(register.getUsername());
        userRepository.save(user);

        return true;
    }
}

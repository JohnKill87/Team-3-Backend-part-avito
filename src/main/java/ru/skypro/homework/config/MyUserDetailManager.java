package ru.skypro.homework.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;

public class MyUserDetailManager implements UserDetailsManager {
    private final UserRepository userRepository;
    public MyUserDetailManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(MyUserDetails::new)
                .orElseThrow();
    }

    @Override
    public void createUser(UserDetails userDetails) {
        UserEntity user = new UserEntity();
        user.setEmail(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setRole(Role.valueOf(userDetails.getAuthorities().iterator().next().getAuthority().substring(5)));
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserDetails user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteUser(String username) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByEmail(username);
    }
}

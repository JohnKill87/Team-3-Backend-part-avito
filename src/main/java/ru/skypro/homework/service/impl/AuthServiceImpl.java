package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.exception.UserAlredyExsistException;
import ru.skypro.homework.exception.WrongPasswordException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final MyUserDetailService myUserDetailService;

    public AuthServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           MyUserDetailService myUserDetailService) {
        this.encoder = passwordEncoder;
        this.userRepository = userRepository;
        this.myUserDetailService = myUserDetailService;
    }


    @Override
    public boolean login(String userName, String password) {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        UserDetails userDetails = myUserDetailService.loadUserByUsername(userName);
        if (!encoder.matches(password, userDetails.getPassword())) {
            throw new WrongPasswordException("Неверный пароль");
        }
        return true;
    }


    @Override
    public boolean register(Register register) {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        UserEntity user = UserMapper.mapFromRegisterToUserEntity(register);
        if (userRepository.findUserEntityByUserName(user.getUserName()) != null) {
            throw new UserAlredyExsistException("Такой пользователь существует");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

}

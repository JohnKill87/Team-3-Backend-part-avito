package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.exception.UserAlredyExsistException;
import ru.skypro.homework.exception.WrongPasswordException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

/**
 * Реализация сервиса {@link AuthService} для аутентификации пользователей.
 */
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

    /**
     * Метод сервиса для аутентификации пользователей по логину и паролю которые содержатся в БД.
     * Используется метод сервиса {@link MyUserDetailService#loadUserByUsername(String)}
     * @return {@link Boolean}
     */
    @Override
    public boolean login(String userName, String password) {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        UserDetails userDetails = myUserDetailService.loadUserByUsername(userName);
        if (!encoder.matches(password, userDetails.getPassword())) {
            throw new WrongPasswordException("Неверный пароль");
        }
        return true;
    }

    /**
     * Метод сервиса для регистрации пользователей и сохранения их в БД.
     * Используется методы репозитория {@link UserRepository#findUserEntityByUserName(String)}, {@link UserRepository#save(Object)}
     * и маппер {@link UserMapper#mapFromRegisterToUserEntity(Register)}
     * @return {@link Boolean}
     */
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

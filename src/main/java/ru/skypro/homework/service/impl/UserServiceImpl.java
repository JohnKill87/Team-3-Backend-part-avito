package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.dto.UserGetDto;
import ru.skypro.homework.exception.PasswordIsNotMatchException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UsersMapper;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Value("${path.to.avatars.folder}")
    private String photoAvatar;
    private final UserRepository userRepository;
    private final UsersMapper usersMapper;
    private final PasswordEncoder encoder;

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

        UserEntity userEntity = null;
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String phone = user.getPhone();

        userEntity = getUserByEmail(authentication.getName());
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setPhone(phone);

        userRepository.save(userEntity);

        return user;
    }

    @Override
    public UserGetDto getUserInfo(Authentication authentication) {
        UserEntity userEntity = userRepository.findByEmail(authentication.getName()).orElse(null);
        return usersMapper.mapToUserGetDto(userEntity);
    }

    @Override
    public void updateUserAvatar(MultipartFile avatar, Authentication authentication) {
        try {
            UserEntity userEntity = getUserByEmail(authentication.getName());
            uploadAvatar(userEntity, avatar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByEmail(String username) {
        return userRepository.existsByEmail(username);
    }

    @Override
    public UserEntity getUser(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public void createUser(Register register) {
        UserEntity userEntity = usersMapper.toEntity(register);

        String password = encoder.encode(register.getPassword());
        userEntity.setPassword(password);
        userRepository.save(userEntity);
    }

    @Override
    public byte[] getAvatar(Integer id) {
        UserEntity userEntity = getUser(id);
        try {
            return Files.readAllBytes(Path.of(userEntity.getAvatar()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void uploadAvatar(UserEntity userEntity, MultipartFile avatar) throws IOException {
        Path filepath = Path.of(photoAvatar, userEntity.hashCode() + "." + StringUtils.getFilenameExtension(avatar.getOriginalFilename()));
        Files.createDirectories(filepath.getParent());
        Files.deleteIfExists(filepath);

        try (
                InputStream is = avatar.getInputStream();
                OutputStream os = Files.newOutputStream(filepath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
            userEntity.setAvatar(filepath.toString());
            userRepository.save(userEntity);
        }
    }
    @Override
    public UserEntity getUserByEmail(String userName) {
        return userRepository.findByEmail(userName).orElse(null);
    }
}

package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final ImageService imageService;
    /**
     * Set user's password
     * @param email target user's email (username)
     * @param newPasswordDto object with new and current passwords
     * @return {@code true} if password successfully changed, <br>
     * {@code false} if current password is incorrect
     */
    public boolean setPassword(String email, NewPassword newPasswordDto) {
        User targetUser = findUserByEmail(email);
        if (encoder.matches(newPasswordDto.getCurrentPassword(), targetUser.getEncodedPassword())) {
            targetUser.setEncodedPassword(encoder.encode(newPasswordDto.getNewPassword()));
            save(targetUser);
            return true;
        }
        return false;
    }

    public UserDto findUserDtoByEmail(String email) {
        return userMapper.mapEntityToDto(findUserByEmail(email));
    }


    public User findUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }


    public void save(User user) {
        repository.save(user);
    }


    public void saveUserFromRegReq(RegisterReq registerReq, Role role, String encodedPassword) {
        repository.save(userMapper.mapRegReqToUser(registerReq, role, encodedPassword));
    }

    /**
     * Edit user
     * @param targetEmail target user's email (username)
     * @param dto {@link UserDto} with user's new data
     * @return edited user in {@link UserDto} instance
     */
    public UserDto editUser(String targetEmail, UserDto dto) {
        User targetUser = findUserByEmail(targetEmail);
        targetUser.setFirstName(dto.getFirstName());
        targetUser.setLastName(dto.getLastName());
        targetUser.setPhone(dto.getPhone());
        save(targetUser);
        return userMapper.mapEntityToDto(targetUser);
    }

    /**
     * Upload user's avatar
     * @param targetEmail target user's email (username)
     * @param image {@link MultipartFile} with avatar
     */
    @Transactional
    public void uploadImage(String targetEmail, MultipartFile image) {
        User targetUser = findUserByEmail(targetEmail);
        targetUser.setImage(imageService.uploadUserAvatar(targetEmail, image));
        save(targetUser);
    }
}
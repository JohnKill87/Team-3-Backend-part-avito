package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.User;

import java.util.ArrayList;

@Component
public class UserMapper {
    /**
     * Map {@link User} to {@link UserDto}
     * @param user target {@link User}
     * @return created {@link UserDto}
     */
    public UserDto mapEntityToDto(User user) {
        return new UserDto(
                user.getUserId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getImage()
        );
    }

    /**
     * Create new {@link User} from register request
     * @param regReq register request
     * @param role user's role
     * @param encodedPassword user's encoded password
     * @return created {@link User}
     */
    public User mapRegReqToUser(RegisterReq regReq, Role role, String encodedPassword) {
        return new User(
                regReq.getUsername(),
                regReq.getFirstName(),
                regReq.getLastName(),
                regReq.getPhone(),
                role,
                null,
                encodedPassword,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}

package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.dto.UserGetDto;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.impl.AuthServiceImpl;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService, AuthServiceImpl authService) {
        this.userService = userService;
    }

    @PostMapping(value = "/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword password, Authentication authentication) {
        userService.changePassword(password, authentication);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/me")
    public ResponseEntity<?> getUser(Authentication authentication) {
        UserGetDto user = userService.getUserInfo(authentication);
        return ResponseEntity.ok(user);
    }


    @PatchMapping(value = "/me")
    public ResponseEntity<User> updateUser(@RequestBody User user, Authentication authentication) {
        if (user != null) {
            User userDto = userService.updateUserEntity(user, authentication);
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(@RequestParam MultipartFile avatar) {
        return ResponseEntity.ok().build();
    }
}

package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.impl.AuthServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthServiceImpl authService;
    public UserController(UserService userService, AuthServiceImpl authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/set_password")
    public ResponseEntity<NewPassword> setPassword(@RequestBody NewPassword password, Authentication authentication) {
        NewPassword resultPassword = new NewPassword();
        authService.changePassword(
                authentication.getName(),
                password.getCurrentPassword(),
                password.getNewPassword())
                .ifPresent(resultPassword::setCurrentPassword);
            return ResponseEntity.ok(resultPassword);
    }

    @GetMapping("/me")
    public ResponseEntity<UserEntity> getUser() {
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/me")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if (user != null) {
            userService.updateUserEntity(user);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(@RequestParam MultipartFile avatar) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    public String test() {
        return "TEST";
    }
}

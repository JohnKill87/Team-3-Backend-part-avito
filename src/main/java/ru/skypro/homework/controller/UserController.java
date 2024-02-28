package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.dto.UserGetDto;
import ru.skypro.homework.service.UserService;


@RestController
@RequestMapping("/users")
@Tag(name = "Users")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword password, Authentication authentication) {
        userService.changePassword(password, authentication);
        return ResponseEntity.ok().build();
    }

    @GetMapping("me")
    public ResponseEntity<UserGetDto> getUserInfo(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserInfo(authentication));
    }

    @GetMapping(value = "/{id}/avatar", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getAvatar(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getAvatar(id));
    }

    @PatchMapping(value = "me")
    public ResponseEntity<User> updateUser(@RequestBody User user, Authentication authentication) {
        return ResponseEntity.ok(userService.updateUserEntity(user, authentication));
    }


    @PatchMapping(value = "me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserAvatar(@RequestParam MultipartFile image, Authentication authentication) {
        userService.updateUserAvatar(image, authentication);
        return ResponseEntity.ok().build();
    }
}

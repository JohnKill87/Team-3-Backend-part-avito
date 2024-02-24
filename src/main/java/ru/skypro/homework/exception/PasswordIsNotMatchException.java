package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Пароль не совпал.")
public class PasswordIsNotMatchException extends RuntimeException {
    public PasswordIsNotMatchException() {
    }
}
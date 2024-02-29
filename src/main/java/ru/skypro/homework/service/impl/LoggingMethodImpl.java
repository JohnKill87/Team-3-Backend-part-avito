package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.skypro.homework.service.LoggingMethod;

/**
 * Реализация сервиса {@link LoggingMethod} для работы с логами.
 */
public class LoggingMethodImpl implements LoggingMethod {
    public static String getMethodName() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        return stackTraceElements[2].getMethodName();
    }
}


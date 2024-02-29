package ru.skypro.homework.exception;

public class AdNotFoundException extends RuntimeException {
    private final Integer id;

    public AdNotFoundException(Integer id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "User with id = " + id + " not found!";
    }
}

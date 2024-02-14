package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.UserEntity;

@Data
public class Ad {

    private int id;
    private UserEntity author;
    private String title;
    private String description;
    private int price;
    private String image;
}

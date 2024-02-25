package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.UserEntity;

@Data
public class AdDto {

    private int pk;
    private int author;
    private String title;
    private int price;
    private String image;
}

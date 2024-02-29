package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.UserEntity;

@Data
public class AdDto {

    private Integer pk;

    private Integer author;

    private String image;

    private Integer price;

    private String title;
}

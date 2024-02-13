package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class Comment {

    private Integer author; // id автора комментария

    private String authorImage; // ссылка на аватар автора

    private String authorFirstname; // имя автора комментария

    private Long createdAt; // дата и время создания комментария

    private Integer pk; // id комментария

    private String text; // текст комментария

}
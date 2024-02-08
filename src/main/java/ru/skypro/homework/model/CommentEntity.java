package ru.skypro.homework.model;

import javax.persistence.Entity;

@Entity
public class CommentEntity {
    private Integer id; // id комментария
    private String text; // текст комментария
    private Long createdAT; // дата и время создания комментария
//    private UserEntity author // автор комментария
//    private AdEntity ad; // объявление, к которому прикреплен комментарий
}

package ru.skypro.homework.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id комментария
    private String text; // текст комментария
    private Long createdAT; // дата и время создания комментария
//    private UserEntity author // автор комментария
//    private AdEntity ad; // объявление, к которому прикреплен комментарий
}

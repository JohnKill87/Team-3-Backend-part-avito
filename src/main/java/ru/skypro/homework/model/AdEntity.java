package ru.skypro.homework.model;

import lombok.Data;
import ru.skypro.homework.dto.User;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ads")
public class AdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserEntity author;

    private String image;

    private String title;

    private String description;

    private int price;
}

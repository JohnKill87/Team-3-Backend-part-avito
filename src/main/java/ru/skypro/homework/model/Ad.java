package ru.skypro.homework.model;

import lombok.Data;
import org.apache.catalina.User;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ads")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int price;

}

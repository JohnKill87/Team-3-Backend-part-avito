package ru.skypro.homework.entity;

import lombok.Data;
import org.apache.catalina.User;

import javax.persistence.Entity;

@Data
@Entity(name = "ads")
public class Ad {

    private User author;
    private String title;
    private String description;
    private int price;

}

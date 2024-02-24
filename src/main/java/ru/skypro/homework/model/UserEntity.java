package ru.skypro.homework.model;

import lombok.Getter;
import lombok.Setter;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "users")
@Setter
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany(mappedBy = "author")
    private List<CommentEntity> comments;

    @OneToMany
    private List<AdEntity> ads;

    @OneToOne
    @JoinColumn(name = "image")
    private AvatarEntity avatar;

}

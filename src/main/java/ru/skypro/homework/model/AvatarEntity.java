package ru.skypro.homework.model;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@Data
@Table(name = "avatars")
public class AvatarEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String filePath;

    private Long fileSize;

    private String mediaType;

    @Lob
    private byte[] data;

    @OneToOne
    private UserEntity user;

    @OneToOne
    private AdEntity ad;
}

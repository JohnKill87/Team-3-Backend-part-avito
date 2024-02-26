package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.ImageEntity;

public interface ImageService {

    ImageEntity addImage(MultipartFile image);
    ImageEntity updateImage(MultipartFile image, ImageEntity image2);
    byte[] getImage(String id);
}

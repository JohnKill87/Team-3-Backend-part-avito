package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.ImageEntity;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public ImageEntity addImage(MultipartFile image) {

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setId(UUID.randomUUID().toString());

        try {
            imageEntity.setBytes(image.getBytes());
            byte[] bytes = image.getBytes();
            imageEntity.setBytes(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return imageRepository.save(imageEntity);
    }

    @Override
    public ImageEntity updateImage(MultipartFile image, ImageEntity image2) {

        try {
            image2.setBytes(image.getBytes());
            byte[] bytes = image.getBytes();
            image2.setBytes(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return imageRepository.save(image2);
    }

    @Override
    public byte[] getImage(String id) {

        ImageEntity imageEntity = imageRepository.findById(id).orElseThrow();
        return imageEntity.getBytes();
    }
}

package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.model.ModelEntity;
import ru.skypro.homework.model.PhotoEntity;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.PhotoRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.lang.reflect.Array;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * Реализация сервиса {@link ImageService} для работы с Изображениями.
 */
@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    private final PhotoRepository photoRepository;
    private final UserMapper userMapper;

    @Value("${path.to.photos.folder}")
    private String photoDir;


    public ImageServiceImpl(PhotoRepository photoRepository, UserMapper userMapper) {
        this.photoRepository = photoRepository;
        this.userMapper = userMapper;
    }

    /**
     * Метод сервиса для добавления и сохранения изображения.
     * Используется методы репозитория {@link PhotoRepository#delete(Object)}, {@link PhotoRepository#save(Object)}
     * и маппер {@link UserMapper#mapMuptipartFileToPhoto(MultipartFile)}
     * @return {@link ModelEntity}
     */
    @Override
    public ModelEntity updateEntitiesPhoto(MultipartFile image, ModelEntity entity) throws IOException {
        //если у сущности уже есть картинка, то нужно ее удалить
        if (entity.getPhoto() != null) {
            photoRepository.delete(entity.getPhoto());
        }

        //заполняем поля photo и сохраняем фото в БД
        PhotoEntity photoEntity = userMapper.mapMuptipartFileToPhoto(image);
        log.info("Создана сущность photoEntity - {}", photoEntity != null);
        entity.setPhoto(photoEntity);
        photoRepository.save(photoEntity);

        //адрес до директории хранения фото на ПК
        Path filePath = Path.of(photoDir, entity.getPhoto().getId() + "."
                + this.getExtension(image.getOriginalFilename()));
        log.info("путь к файлу для хранения фото на ПК: {}", filePath);

        //добавляем в сущность фото путь где оно хранится на ПК
        entity.getPhoto().setFilePath(filePath.toString());

        //добавляем в сущность путь на ПК
        entity.setFilePath(filePath.toString());

        //сохранение на ПК
        this.saveFileOnDisk(image, filePath);

        return entity;
    }

    /**
     * Метод сервиса для сохранения изображения на диске.
     * @return {@link Boolean}
     */
    @Override
    public boolean saveFileOnDisk(MultipartFile image, Path filePath) throws IOException {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (InputStream is = image.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        return true;
    }

    /**
     * Метод сервиса для получения изображения с диска.
     * @return {@link Array} of {@link Byte}
     */
    public byte[] getPhotoFromDisk(PhotoEntity photo) {
        Path path1 = Path.of(photo.getFilePath());
        try {
            return Files.readAllBytes(path1);
        } catch (IOException e) {
            throw new NoSuchFieldException("Искомый файл аватара или фото объявления, отсутствует на ПК\n" +
                    "Поиск файла перенаправлен в БД");
        } finally {
            return null;
        }
    }

    /**
     * Дополнительный метод для получения расширения файла из его названия.
     * @return {@link String}
     */
    @Override
    public String getExtension(String fileName) {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}

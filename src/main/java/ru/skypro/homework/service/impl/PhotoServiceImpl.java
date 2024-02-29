package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exception.PhotoOnDatabaseIsAbsentException;
import ru.skypro.homework.exception.PhotoOnPcIsAbsentException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.ModelEntity;
import ru.skypro.homework.model.PhotoEntity;
import ru.skypro.homework.repository.PhotoRepository;
import ru.skypro.homework.service.PhotoService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.lang.reflect.Array;

/**
 * Реализация сервиса {@link PhotoService} для работы с изображениями.
 */
@Service
@Slf4j
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;
    private final ImageServiceImpl imageService;

    public PhotoServiceImpl(PhotoRepository photoRepository, ImageServiceImpl imageService) {
        this.photoRepository = photoRepository;
        this.imageService = imageService;
    }

    /**
     * Метод сервиса для получения изображения с диска или БД.
     * Используется метод репозитория {@link PhotoRepository#findById(Integer)} и сервиса {@link ImageService#getPhotoFromDisk(PhotoEntity)}
     * @return {@link Array} of {@link Byte}
     */
    public byte[] getPhoto(Integer photoId) throws IOException {
        log.info("Запущен метод сервиса {}", LoggingMethodImpl.getMethodName());
        log.info("photoId: {}", photoId);

        PhotoEntity photo = photoRepository.findById(photoId).orElseThrow(PhotoOnDatabaseIsAbsentException::new);
        log.info("Фото найдено - {}", photo.getData() != null);

        //Если картинка запрошенная с ПК не получена по какой-то причине, достаем ее из БД
        if (imageService.getPhotoFromDisk(photo) == null) {
            return photoRepository.findById(photoId).orElseThrow(PhotoOnPcIsAbsentException::new).getData();
        }
        //Если предыдущее условие не выполнилось и с картинкой все в порядке, то достаем ее с ПК
        return imageService.getPhotoFromDisk(photo);
    }

}

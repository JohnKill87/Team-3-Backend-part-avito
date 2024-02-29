package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.mapper.UsersMapper;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    @Value("${path.to.ad.photo}")
    private String photoPath;
    private final AdMapper adMapper;
    private final AdRepository adRepository;
    private final UserService userService;

    @Override
    @Transactional
    public AdDto createAd(CreateOrUpdateAd createOrUpdateAdDto, MultipartFile image, Authentication authentication) {
        try {
            AdEntity adEntity = adRepository.save(adMapper.toEntity(createOrUpdateAdDto));
            UserEntity userEntity = userService.getUserByEmail(authentication.getName());
            adEntity.setUser(userEntity);
            return adMapper.toAdDto(uploadImage(adEntity, image));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private AdEntity uploadImage(AdEntity adEntity, MultipartFile image) throws IOException {
        Path filePath = Path.of(photoPath, adEntity.hashCode() + "." + StringUtils.getFilenameExtension(image.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = image.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
            adEntity.setImage(filePath.toString());
            return adRepository.save(adEntity);
        }
    }

    @Override
    public AdEntity getAd(Integer id) {
        return adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
    }

    @Override
    public AdsDto getAll() {
        return adMapper.toAdsDto(adRepository.findAll());
    }

    @Override
    public ExtendedAd getInfoAboutAd(Integer id) {
        return adMapper.toExtendedAdDto(getAd(id));
    }

    @Override
    public byte[] getImage(Integer id) {
        try {
            AdEntity adEntity = getAd(id);
            return Files.readAllBytes(Path.of(adEntity.getImage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAd(Integer id, Authentication authentication) {
        if (isAdminOrOwner(id, authentication)) {
            try {
                AdEntity adEntity = getAd(id);
                Files.deleteIfExists(Path.of(adEntity.getImage()));
                adRepository.delete(adEntity);
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new ForbiddenException("No permission to delete this ad");
    }

    @Override
    public AdDto updateAd(Integer id, CreateOrUpdateAd createOrUpdateAdDto, Authentication authentication) {
        if (isAdminOrOwner(id, authentication)) {
            AdEntity adEntity = getAd(id);

            adEntity.setTitle(createOrUpdateAdDto.getTitle());
            adEntity.setDescription(createOrUpdateAdDto.getDescription());
            adEntity.setPrice(createOrUpdateAdDto.getPrice().toString());

            return adMapper.toAdDto(adRepository.save(adEntity));
        }
        throw new ForbiddenException("No permission to edit this ad");
    }

    @Override
    public AdsDto getMyAds(Authentication authentication) {
        UserEntity userEntity = userService.getUserByEmail(authentication.getName());
        return adMapper.toAdsDto(adRepository.findAllByUserIdOrderByPk(userEntity.getId()));
    }

    @Override
    public byte[] updateAdImage(Integer id, MultipartFile image, Authentication authentication) {
        if (isAdminOrOwner(id, authentication)) {
            try {
                AdEntity adEntity = getAd(id);
                adEntity = uploadImage(adEntity, image);
                return Files.readAllBytes(Path.of(adEntity.getImage()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new ForbiddenException("No permission to update this image");
    }

    private boolean isAdminOrOwner(Integer id, Authentication authentication) {
        AdEntity adEntity = getAd(id);
        UserEntity userEntity = userService.getUserByEmail(authentication.getName());
        return userEntity.equals(adEntity.getUser()) || userEntity.getRole().equals(Role.ADMIN.toString());
    }
}

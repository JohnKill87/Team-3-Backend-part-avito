package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.model.AdEntity;

public interface AdService {

    AdDto createAd(CreateOrUpdateAd createOrUpdateAdDto, MultipartFile image, Authentication authentication);

    AdEntity getAd(Integer id);

    AdsDto getAll();

    ExtendedAd getInfoAboutAd(Integer id);

    byte[] getImage(Integer id);

    void deleteAd(Integer id, Authentication authentication);

    AdDto updateAd(Integer id, CreateOrUpdateAd createOrUpdateAdDto, Authentication authentication);

    AdsDto getMyAds(Authentication authentication);

    byte[] updateAdImage(Integer id, MultipartFile image, Authentication authentication);




}

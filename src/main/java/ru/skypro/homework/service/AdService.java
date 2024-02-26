package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.model.AdEntity;

public interface AdService {

    ExtendedAd getAdsById(Integer id);
    AdDto addAd(CreateOrUpdateAd properties, Authentication authentication, MultipartFile image);
    AdsDto getAllAds();
    void deleteAds(Integer id);
    AdDto updateAds(Integer id, CreateOrUpdateAd dto);
    AdsDto getAdsUser(String userName);
    void updateImage(Integer id, MultipartFile image);




}

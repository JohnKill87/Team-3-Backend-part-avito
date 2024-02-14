package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.model.AdEntity;

public interface AdService {

    AdEntity getAdsById(Integer id);
    Ad addAd(CreateOrUpdateAd properties, MultipartFile image);
    Ads getAllAds();
    void deleteAds(int id);
    Ad updateAds(Integer id, CreateOrUpdateAd dto);
    Ads getAdsMe(String username);
    void updateImage(Integer id, MultipartFile image);




}

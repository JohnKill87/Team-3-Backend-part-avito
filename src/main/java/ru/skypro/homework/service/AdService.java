package ru.skypro.homework.service;

import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.model.AdEntity;

public interface AdService {

    AdEntity getAdsById(Integer id);
    AdsDto getAllAds();
    void deleteAds(int id);
}

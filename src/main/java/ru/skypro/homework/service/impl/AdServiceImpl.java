package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;

@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;

    public AdServiceImpl(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    @Override
    public AdEntity getAdsById(Integer id) {
        return adRepository.findById(id)
                .orElseThrow(() -> new AdNotFoundException("Объявление не найдено"));
    }

    @Override
    public AdsDto getAllAds() {
        return (AdsDto) adRepository.findAll();
    }

    @Override
    public void deleteAds(int id) {
        AdEntity removedAd = getAdsById(id);
        adRepository.delete(removedAd);
    }
}

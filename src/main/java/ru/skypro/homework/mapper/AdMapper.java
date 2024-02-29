package ru.skypro.homework.mapper;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.model.AdEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdMapper {

    public AdEntity toEntity(CreateOrUpdateAd createOrUpdateAd) {
        AdEntity ad = new AdEntity();

        ad.setTitle(createOrUpdateAd.getTitle());
        ad.setDescription(createOrUpdateAd.getDescription());
        ad.setPrice(createOrUpdateAd.getPrice().toString());

        return ad;
    }

    public AdDto toAdDto(AdEntity adEntity) {
        AdDto adDto = new AdDto();

        adDto.setPk(adEntity.getPk());
        adDto.setAuthor(adEntity.getUser().getId());
        adDto.setImage("/" + adEntity.getPk() + "/image");
        adDto.setPrice(Integer.parseInt(adEntity.getPrice()));
        adDto.setTitle(adEntity.getTitle());

        return adDto;
    }

    public AdsDto toAdsDto(List<AdEntity> ads) {
        AdsDto adsDto = new AdsDto();
        List<AdDto> adDtoList = ads.stream()
                .map(this::toAdDto)
                .collect(Collectors.toList());

        adsDto.setCount(adDtoList.size());
        adsDto.setResults(adDtoList);

        return adsDto;
    }

    public ExtendedAd toExtendedAdDto(AdEntity adEntity) {
        ExtendedAd extendedAdDto = new ExtendedAd();

        extendedAdDto.setPk(adEntity.getPk());
        extendedAdDto.setAuthorFirstName(adEntity.getUser().getFirstName());
        extendedAdDto.setAuthorLastName(adEntity.getUser().getLastName());
        extendedAdDto.setDescription(adEntity.getDescription());
        extendedAdDto.setEmail(adEntity.getUser().getEmail());
        extendedAdDto.setImage("/" + adEntity.getPk() + "/image");
        extendedAdDto.setPhone(adEntity.getUser().getPhone());
        extendedAdDto.setPrice(Integer.parseInt(adEntity.getPrice()));
        extendedAdDto.setTitle(adEntity.getTitle());

        return extendedAdDto;
    }
}

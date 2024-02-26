package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.UserEntity;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class AdMapper {

//    Из Entity в DTO
    public AdDto mapToAdDTO(AdEntity adEntity) {
        AdDto adDto = new AdDto();
        adDto.setPk(adEntity.getId());
        adDto.setAuthor(adEntity.getAuthor());
        adDto.setTitle(adEntity.getTitle());
        adDto.setPrice(adEntity.getPrice());
        adDto.setImage("/ads/" + adEntity.getImage().getId() + "/image");
        return adDto;
    }

    public ExtendedAd mapToExtendedAdDTO(AdEntity adEntity) {
        ExtendedAd extendedAd = new ExtendedAd();
        extendedAd.setPk(adEntity.getId());
        extendedAd.setAuthorFirstName(adEntity.getAuthor().getFirstName());
        extendedAd.setAuthorLastName(adEntity.getAuthor().getLastName());
        extendedAd.setDescription(adEntity.getDescription());
        extendedAd.setEmail(adEntity.getAuthor().getEmail());
        extendedAd.setImage("/ads/" + adEntity.getImage().getId() + "/image");
        extendedAd.setPhone(adEntity.getAuthor().getPhone());
        extendedAd.setPrice(adEntity.getPrice());
        extendedAd.setTitle(adEntity.getTitle());
        return extendedAd;
    }

    public AdsDto adsMap(Collection<AdEntity> ads) {
        AdsDto result = new AdsDto();
        result.setResults(
                ads.stream()
                        .map(this::mapToAdDTO)
                        .collect(Collectors.toList())
        );
        result.setCount(ads.size());
        return result;
    }

    public AdEntity mapCreateOrUpdateAd(CreateOrUpdateAd createOrUpdateAd) {
        AdEntity adEntity = new AdEntity();
        adEntity.setTitle(createOrUpdateAd.getTitle());
        adEntity.setDescription(createOrUpdateAd.getDescription());
        adEntity.setPrice(createOrUpdateAd.getPrice());
        return adEntity;
    }

//    Из DTO в Entity
    public AdEntity mapToAdEntity(AdDto adDto) {
        AdEntity adEntity = new AdEntity();
        adEntity.getAuthor().setId(adDto.getAuthor().getId());
        adEntity.setTitle(adDto.getTitle());
        adEntity.setPrice(adDto.getPrice());
        adEntity.getImage().setId(adDto.getImage());
        return adEntity;
    }
}

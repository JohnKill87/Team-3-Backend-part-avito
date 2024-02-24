package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.UserEntity;

@Component
public class AdMapper {

//    Из Entity в DTO
    public AdDto mapToAdDTO(AdEntity adEntity) {
        AdDto adDto = new AdDto();
        adDto.setPk(adEntity.getId());
        adDto.setAuthor(adEntity.getAuthor());
        adDto.setTitle(adEntity.getTitle());
        adDto.setPrice(adEntity.getPrice());
        adDto.setImage(adEntity.getImage());
        return adDto;
    }

    public ExtendedAd mapToExtendedAdDTO(AdEntity adEntity) {
        ExtendedAd extendedAd = new ExtendedAd();
        extendedAd.setPk(adEntity.getId());
        extendedAd.setAuthorFirstName(adEntity.getAuthor().getFirstName());
        extendedAd.setAuthorLastName(adEntity.getAuthor().getLastName());
        extendedAd.setDescription(adEntity.getDescription());
        extendedAd.setEmail(adEntity.getAuthor().getEmail());
        extendedAd.setImage(adEntity.getImage());
        extendedAd.setPhone(adEntity.getAuthor().getPhone());
        extendedAd.setPrice(adEntity.getPrice());
        extendedAd.setTitle(adEntity.getTitle());
        return extendedAd;
    }

//    Из DTO в Entity
    public AdEntity mapToAdEntity(AdDto adDto) {
        AdEntity adEntity = new AdEntity();
        adEntity.setId(adDto.getPk());
        adEntity.setAuthor(adDto.getAuthor());
        adEntity.setTitle(adDto.getTitle());
        adEntity.setPrice(adDto.getPrice());
        adEntity.setImage(adDto.getImage());
        return adEntity;
    }
}

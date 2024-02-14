package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.model.AdEntity;

@Service
public class AdMapper {

//    Из Entity в DTO
    public Ad mapToAdDTO(AdEntity adEntity) {
        Ad adDto = new Ad();
        adDto.setId(adEntity.getId());
        adDto.setAuthor(adEntity.getAuthor());
        adDto.setTitle(adEntity.getTitle());
        adDto.setDescription(adEntity.getDescription());
        adDto.setPrice(adEntity.getPrice());
        adDto.setImage(adEntity.getImage());
        return adDto;
    }

//    Из DTO в Entity
    public AdEntity mapToAdEntity(Ad adDto) {
        AdEntity adEntity = new AdEntity();
        adEntity.setId(adDto.getId());
        adEntity.setAuthor(adDto.getAuthor());
        adEntity.setTitle(adDto.getTitle());
        adEntity.setDescription(adDto.getDescription());
        adEntity.setPrice(adDto.getPrice());
        adEntity.setImage(adDto.getImage());
        return adEntity;
    }
}

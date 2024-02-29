package ru.skypro.homework.mapper;


import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CreateAdDto;
import ru.skypro.homework.dto.FullAdDto;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdMapper {

    /**
     * Map {@link List} of {@link Ad} to {@link ResponseWrapperAds}
     *
     * @param ads {@link List} of {@link Ad}
     * @return created {@link ResponseWrapperAds}
     */
    public ResponseWrapperAds mapAdsListToResponseWrapperAds(List<Ad> ads) {
        List<AdDto> dtoList = ads.stream().map(this::mapEntityToDto).collect(Collectors.toList());
        return new ResponseWrapperAds(dtoList.size(), dtoList);
    }

    /**
     * Map {@link CreateAdDto} to {@link Ad}
     *
     * @param dto target {@link CreateAdDto}
     * @return created {@link Ad}
     */
    public Ad mapCreateAdDtoToAd(CreateAdDto dto) {
        return new Ad(
                new User(),
                "",
                dto.getPrice(),
                dto.getTitle(),
                dto.getDescription(),
                new ArrayList<>()
        );
    }

    /**
     * Map {@link Ad} to {@link AdDto}
     *
     * @param ad target {@link Ad}
     * @return created {@link AdDto}
     */
    public AdDto mapEntityToDto(Ad ad) {
        return new AdDto(
                ad.getAuthor().getUserId(),
                ad.getImage(),
                ad.getPk(),
                ad.getPrice(),
                ad.getTitle()
        );
    }

    /**
     * Map {@link Ad} and {@link User} to {@link FullAdDto}
     *
     * @param ad     target {@link Ad}
     * @param author target {@link User}
     * @return created {@link FullAdDto}
     */
    public FullAdDto mapAdAndAuthorToFullAdDto(Ad ad, User author) {
        return new FullAdDto(
                ad.getPk(),
                author.getFirstName(),
                author.getLastName(),
                ad.getDescription(),
                author.getEmail(),
                ad.getImage(),
                author.getPhone(),
                ad.getPrice(),
                ad.getTitle()
        );
    }
}

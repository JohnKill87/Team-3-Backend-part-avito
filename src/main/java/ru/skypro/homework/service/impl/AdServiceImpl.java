package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.mapper.UsersMapper;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.ImageEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdMapper adMapper;
    private final UsersMapper usersMapper;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ImageService imageService;

    //Получение всех объявлений
    @Override
    public AdsDto getAllAds() {
        return adMapper.adsMap(adRepository.findAll());
    }

    //Добавление объявления
    @Override
    public AdDto addAd(CreateOrUpdateAd properties, Authentication authentication, MultipartFile image) {
        AdEntity adEntity = adMapper.mapCreateOrUpdateAd(properties);
        adEntity.setAuthor(usersMapper.mapToUserEntity(userService.getUserInfo(authentication)));
        ImageEntity imageEntity = imageService.addImage(image);
        adEntity.setImage(imageEntity);

        return adMapper.mapToAdDTO(adRepository.save(adEntity));
    }

    //Получение информации об объявлении
    @Override
    public ExtendedAd getAdsById(Integer id) {
        AdEntity ad = adRepository.findById(id).get();
        return adMapper.mapToExtendedAdDTO(ad);
    }

    //Удаление объявления
    @Override
    public void deleteAds(Integer id) {
        AdEntity ad = adRepository.findById(id).get();
        adRepository.delete(ad);
    }

    //Обновление информации об объявлении
    @Override
    public AdDto updateAds(Integer id, CreateOrUpdateAd dto) {
        AdEntity adEntity = adRepository.findById(id).get();

        adEntity.setTitle(dto.getTitle());
        adEntity.setDescription(dto.getDescription());
        adEntity.setPrice(dto.getPrice());

        adRepository.save(adEntity);
        return adMapper.mapToAdDTO(adEntity);
    }

    //Получение объявлений авторизованного пользователя
    @Override
    public AdsDto getAdsUser(String userName) {
        UserEntity user = userRepository.findUserByEmail(userName);

        List<AdDto> ads = adRepository.findByAuthor(user).stream()
                .map(adEntity -> adMapper.mapToAdDTO(adEntity))
                .collect(Collectors.toList());

        AdsDto adsDto = new AdsDto();
        return adsDto;
    }

    //Обновление картинки объявления
    @Override
    public void updateImage(Integer id, MultipartFile image) {
        AdEntity adEntity = adRepository.findById(id).orElseThrow();
        ImageEntity imageEntity = imageService.updateImage(image, adEntity.getImage());
        adEntity.setImage(imageEntity);
        adRepository.save(adEntity);
    }
}

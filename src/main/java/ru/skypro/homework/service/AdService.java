package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdService {

    private final AdRepository repository;
    private final AdMapper adMapper;
    private final UserService userService;
    private final ImageService imageService;

    /**
     * Return all ads
     * @return {@link List<Ad>} of {@link Ad}
     */
    public List<Ad> findAll() {
        return repository.findAll();
    }

    /**
     * Return target ad
     * @param id id of target ad
     * @return {@link Optional<Ad>} with target {@link Ad}, <br>
     * {@link Optional#empty()} if target ad not existed
     */
    public Optional<Ad> findById(int id) {
        return repository.findById(id);
    }

    /**
     * Create new ad
     * @param username author's username (email)
     * @param image new ad's image
     * @param createAdsDto new ad's data
     * @return created ad in {@link AdDto} instance
     */
    @Transactional
    public AdDto createAd(String username, MultipartFile image, CreateAdDto createAdsDto) {
        Ad newAd = adMapper.mapCreateAdDtoToAd(createAdsDto);
        newAd.setAuthor(userService.findUserByEmail(username));
        newAd = save(newAd);

        newAd.setImage(imageService.uploadAdImage(newAd.getPk(), image));
        save(newAd);

        return adMapper.mapEntityToDto(newAd);
    }

    /**
     * Create {@link FullAdDto} instance
     * @param ad target {@link Ad}
     * @return {@link FullAdDto} instance for target {@link Ad}
     */
    public FullAdDto createFullAd(Ad ad) {
        User author = ad.getAuthor();
        return adMapper.mapAdAndAuthorToFullAdDto(ad, author);
    }

    /**
     * Edit target ad
     * @param targetAd target {@link Ad}
     * @param newData {@link CreateAdDto} with new ad's data
     * @return edited ad in {@link AdDto} instance
     */
    public AdDto editAd(Ad targetAd, CreateAdDto newData) {
        targetAd.setDescription(newData.getDescription());
        targetAd.setPrice(newData.getPrice());
        targetAd.setTitle(newData.getTitle());
        save(targetAd);
        return adMapper.mapEntityToDto(targetAd);
    }

    /**
     * Edit ad's image
     * @param ad target {@link Ad}
     * @param imageFile {@link MultipartFile} with new image
     * @return {@code byte[]} - bytes of new image
     */
    @Transactional
    public byte[] editAdImage(Ad ad, MultipartFile imageFile) {
        imageService.uploadAdImage(ad.getPk(), imageFile);
        return imageService.getAdImage(ad.getPk().toString());
    }

    public Ad save(Ad ad) {
        return repository.save(ad);
    }

    /**
     * Delete target ad and it's image
     * @param id id of target ad
     */
    public void deleteById(int id) {
        imageService.deleteAdImage(id);
        repository.deleteById(id);
    }
}

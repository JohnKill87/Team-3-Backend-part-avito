package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.impl.AdServiceImpl;


import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;

    public AdController(AdServiceImpl adService) {
        this.adService = adService;
    }

    @GetMapping("{id}")
    public ResponseEntity<ExtendedAd> getAds(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(adService.getAdsById(id));
    }

    @GetMapping
    public ResponseEntity<AdsDto> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @DeleteMapping
    public void removeAds(@PathVariable("id") Integer id) {
        adService.deleteAds(id);
    }

    @PostMapping
    public ResponseEntity<AdDto> addAds(@RequestPart CreateOrUpdateAd properties,
                                        Authentication authentication,
                                        @RequestPart String image) {
        return ResponseEntity.ok(adService.addAd(properties, authentication, image));
    }

    @PatchMapping("{id}")
    public ResponseEntity<AdDto> announcementInformation(@PathVariable("id") Integer id,
                                                         @RequestBody CreateOrUpdateAd dto) {
        return ResponseEntity.ok(adService.updateAds(id, dto));

    }

    @GetMapping("/me")
    public ResponseEntity<AdsDto> getAdsAuthUser (Authentication authentication) {
        String userName = authentication.getName();
        return ResponseEntity.ok(adService.getAdsUser(userName));
    }

    @PatchMapping("{id}/image")
    public void announcementImage(@PathVariable("id") Integer id,
                                  @RequestPart String image) {
        adService.updateImage(id, image);
    }
}

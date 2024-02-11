package ru.skypro.homework.controller;

import com.sun.istack.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.service.impl.AdServiceImpl;


import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdController {

    private final AdServiceImpl adService;

    public AdController(AdServiceImpl adService) {
        this.adService = adService;
    }

    @GetMapping("{id}")
    public AdEntity getAds(@RequestParam Integer id) {
        return adService.getAdsById(id);
    }

    @GetMapping
    public AdsDto getAllAds() {
        return adService.getAllAds();
    }

    @DeleteMapping
    public void removeAds(@RequestParam int id) {
        adService.deleteAds(id);
    }

    @PostMapping
    public ResponseEntity<Collection<AdEntity>> addAds() {
        return new ResponseEntity<>(Collections.EMPTY_LIST, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<AdEntity> announcementInformation(@PathVariable("id") Integer id,
                                                            @RequestBody CreateOrUpdateAdDto dto) {
        return ResponseEntity.ok().build();

    }

    @GetMapping("/me")
    public ResponseEntity<List<AdsDto>> getAdsAuthUser () {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}/image")
    public ResponseEntity<?> announcementImage(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().build();
    }
}

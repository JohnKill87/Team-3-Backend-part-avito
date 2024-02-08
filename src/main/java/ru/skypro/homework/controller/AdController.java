package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.entity.Ad;


import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdController {

    @GetMapping("/{id}")
    public ResponseEntity<Collection<Ad>> getAds() {
        return new ResponseEntity<>(Collections.EMPTY_LIST, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<Ad>> getAllAds() {
        return new ResponseEntity<>(Collections.EMPTY_LIST, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Collection<Ad>> addAd() {
        return new ResponseEntity<>(Collections.EMPTY_LIST, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Collection<Ad>>  removeAd() {
        return new ResponseEntity<>(Collections.EMPTY_LIST, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Ad> announcementInformation(@PathVariable("id") Integer id,
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

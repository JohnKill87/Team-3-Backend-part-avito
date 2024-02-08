package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.service.AdService;

import java.util.Collection;
import java.util.Collections;

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
}

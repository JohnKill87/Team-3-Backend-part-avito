package ru.skypro.homework.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@RequiredArgsConstructor
public class AdsDto {

    private Integer count;

    private Collection<AdDto> results;

    public AdsDto(Collection<AdDto> results) {
        this.count = results.size();
        this.results = results;
    }
}

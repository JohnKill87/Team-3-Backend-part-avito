package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.config.AdParameters;

import ru.skypro.homework.model.AdEntity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateOrUpdateAd implements AdParameters {

    @NotNull
    @Size(min = AD_TITLE_LENGTH_MIN, max = AD_TITLE_LENGTH_MAX)
    private String title;

    @Min(AD_PRICE_MIN)
    @Max(AD_PRICE_MAX)
    private Integer price;

    @NotNull
    @Size(min = AD_DESCRIPTION_LENGTH_MIN, max = AD_DESCRIPTION_LENGTH_MAX)
    private String description;

}

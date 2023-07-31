package com.smartCode.ecommerce.model.dto.card;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class CardDto {
    @NotBlank
    private String name;

    @NotBlank
    private String cardNumber;

    @NotBlank
    private String expDate;

    @Positive
    private Integer userId;

}

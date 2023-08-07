package com.smartCode.ecommerce.model.dto.card;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CardDto {
    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 16,max = 16)
    private String cardNumber;

    @NotBlank
    @Pattern(regexp = "^(0[1-9]|1[0-2])/?([0-9]{2})$")
    private String expDate;

    @Positive
    private Integer userId;

}

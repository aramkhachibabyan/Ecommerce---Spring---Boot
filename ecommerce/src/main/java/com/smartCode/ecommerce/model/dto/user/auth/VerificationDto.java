package com.smartCode.ecommerce.model.dto.user.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
public class VerificationDto {

    @Positive
    private Integer id;

    @NotBlank
    @Size(min = 6, max = 6)
    String code;
}

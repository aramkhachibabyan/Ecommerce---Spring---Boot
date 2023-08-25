package com.smartCode.ecommerce.model.dto.order;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class OrderCreateDto {
    @NotBlank
    private String note;

}

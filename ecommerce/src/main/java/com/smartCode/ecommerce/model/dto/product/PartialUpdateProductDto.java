package com.smartCode.ecommerce.model.dto.product;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PartialUpdateProductDto {

    @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
    private String name;

    @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
    private String category;

    @Positive
    private BigDecimal price;

    @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
    private String description;

    @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
    private LocalDate productionDate;
}

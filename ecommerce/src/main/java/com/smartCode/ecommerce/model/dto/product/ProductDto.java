package com.smartCode.ecommerce.model.dto.product;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ProductDto {
    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @Positive
    private BigDecimal price;

    @NotBlank
    private String description;

    @NotBlank
    private LocalDate productionDate;
}

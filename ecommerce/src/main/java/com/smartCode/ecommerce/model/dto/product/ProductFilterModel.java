package com.smartCode.ecommerce.model.dto.product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@Component
public class ProductFilterModel {
        @Positive
        private BigDecimal startPrice;
        @Positive
        private BigDecimal endPrice;
        private LocalDate startDate;
        private LocalDate endDate;
    }
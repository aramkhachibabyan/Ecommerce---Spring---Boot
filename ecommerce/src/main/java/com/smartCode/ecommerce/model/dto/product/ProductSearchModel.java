package com.smartCode.ecommerce.model.dto.product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ProductSearchModel {
        private String text;
        private String description;
    }
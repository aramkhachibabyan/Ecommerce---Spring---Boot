package com.smartCode.ecommerce.model.dto.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class ProductFilterSearchRequest {
    private final ProductSearchModel searchModel;
    private final ProductFilterModel filterModel;

}

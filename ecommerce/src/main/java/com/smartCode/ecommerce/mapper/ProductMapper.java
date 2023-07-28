package com.smartCode.ecommerce.mapper;

import com.smartCode.ecommerce.model.dto.product.CreateProductDto;
import com.smartCode.ecommerce.model.dto.product.ResponseProductDto;
import com.smartCode.ecommerce.model.dto.product.UpdateProductDto;
import com.smartCode.ecommerce.model.entity.product.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductEntity toEntity(CreateProductDto productDto);

    ResponseProductDto toDto(ProductEntity product);

    ProductEntity toEntity(UpdateProductDto updateProductDto, @MappingTarget ProductEntity productEntity);
}

package com.smartCode.ecommerce.mapper;

import com.smartCode.ecommerce.model.dto.product.CreateProductDto;
import com.smartCode.ecommerce.model.dto.product.ResponseProductDto;
import com.smartCode.ecommerce.model.dto.product.UpdateProductDto;
import com.smartCode.ecommerce.model.entity.product.ProductEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-29T02:44:03+0400",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductEntity toEntity(CreateProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        productEntity.setName( productDto.getName() );
        productEntity.setCategory( productDto.getCategory() );
        productEntity.setProductionDate( productDto.getProductionDate() );
        productEntity.setPrice( productDto.getPrice() );
        productEntity.setDescription( productDto.getDescription() );

        return productEntity;
    }

    @Override
    public ResponseProductDto toDto(ProductEntity product) {
        if ( product == null ) {
            return null;
        }

        ResponseProductDto responseProductDto = new ResponseProductDto();

        responseProductDto.setName( product.getName() );
        responseProductDto.setCategory( product.getCategory() );
        responseProductDto.setPrice( product.getPrice() );
        responseProductDto.setDescription( product.getDescription() );
        responseProductDto.setProductionDate( product.getProductionDate() );

        return responseProductDto;
    }

    @Override
    public ProductEntity toEntity(UpdateProductDto updateProductDto, ProductEntity productEntity) {
        if ( updateProductDto == null ) {
            return productEntity;
        }

        productEntity.setName( updateProductDto.getName() );
        productEntity.setCategory( updateProductDto.getCategory() );
        productEntity.setProductionDate( updateProductDto.getProductionDate() );
        productEntity.setPrice( updateProductDto.getPrice() );
        productEntity.setDescription( updateProductDto.getDescription() );

        return productEntity;
    }
}

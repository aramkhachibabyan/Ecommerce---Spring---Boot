package com.smartCode.ecommerce.service.product;

import com.smartCode.ecommerce.model.dto.product.CreateProductDto;
import com.smartCode.ecommerce.model.dto.product.PartialUpdateProductDto;
import com.smartCode.ecommerce.model.dto.product.ResponseProductDto;
import com.smartCode.ecommerce.model.dto.product.UpdateProductDto;

import java.util.List;

public interface ProductService {
    ResponseProductDto create (CreateProductDto createProductDto);
    ResponseProductDto getById(Integer id);
    List<ResponseProductDto> getAll();
    ResponseProductDto delete(Integer id);
    ResponseProductDto updateProduct(Integer id, UpdateProductDto updateProductDto);
    ResponseProductDto updateProductPartially(Integer id, PartialUpdateProductDto productDto);
}

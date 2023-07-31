package com.smartCode.ecommerce.service.product.impl;

import com.smartCode.ecommerce.mapper.ProductMapper;
import com.smartCode.ecommerce.model.dto.product.CreateProductDto;
import com.smartCode.ecommerce.model.dto.product.PartialUpdateProductDto;
import com.smartCode.ecommerce.model.dto.product.ResponseProductDto;
import com.smartCode.ecommerce.model.dto.product.UpdateProductDto;
import com.smartCode.ecommerce.model.entity.product.ProductEntity;
import com.smartCode.ecommerce.repository.product.ProductRepository;
import com.smartCode.ecommerce.service.product.ProductService;
import com.smartCode.ecommerce.util.constants.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public ResponseProductDto getById(Integer id) {
        ProductEntity productEntity = productRepository.findById(id).orElse(null);
        return productMapper.toDto(productEntity);
    }

    @Override
    @Transactional
    public ResponseProductDto updateProduct(Integer id, UpdateProductDto updateProductDto) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(Message.PRODUCT_NOT_FOUND));
        ProductEntity entity = productMapper.toEntity(updateProductDto, productEntity);
        ProductEntity save = productRepository.save(entity);
        return productMapper.toDto(save);
    }

    @Override
    @Transactional
    public ResponseProductDto updateProductPartially(Integer id, PartialUpdateProductDto productDto) {
        ProductEntity product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(Message.PRODUCT_NOT_FOUND));

        product.setName(nonNull(productDto.getName()) ? productDto.getName() : product.getName());
        product.setCategory(nonNull(productDto.getCategory()) ? productDto.getCategory() : product.getCategory());
        product.setProductionDate(nonNull(productDto.getProductionDate()) ? productDto.getProductionDate() : product.getProductionDate());
        product.setPrice(nonNull(productDto.getPrice()) ? productDto.getPrice() : product.getPrice());
        product.setDescription(nonNull(productDto.getDescription()) ? productDto.getDescription() : product.getDescription());
        ProductEntity save = productRepository.save(product);
        return productMapper.toDto(save);
    }

    @Override
    @Transactional
    public ResponseProductDto delete(Integer id) {
        ProductEntity product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(Message.PRODUCT_NOT_FOUND));
        productRepository.delete(product);
        return productMapper.toDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseProductDto> getAll() {
        List<ProductEntity> products = productRepository.findAll();
        List<ResponseProductDto> list = new ArrayList<>();
        for (ProductEntity product : products) {
            list.add(productMapper.toDto(product));
        }
        return list;
    }

    @Override
    @Transactional
    public ResponseProductDto create(CreateProductDto createProductDto) {
        ProductEntity entity = productMapper.toEntity(createProductDto);
        ProductEntity save = productRepository.save(entity);
        return productMapper.toDto(save);
    }
}

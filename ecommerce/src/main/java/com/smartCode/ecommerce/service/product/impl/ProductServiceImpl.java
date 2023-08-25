package com.smartCode.ecommerce.service.product.impl;

import com.smartCode.ecommerce.exceptions.ResourceNotFoundException;
import com.smartCode.ecommerce.mapper.ProductMapper;
import com.smartCode.ecommerce.model.dto.product.CreateProductDto;
import com.smartCode.ecommerce.model.dto.product.PartialUpdateProductDto;
import com.smartCode.ecommerce.model.dto.product.ProductFilterSearchRequest;
import com.smartCode.ecommerce.model.dto.product.ResponseProductDto;
import com.smartCode.ecommerce.model.dto.product.UpdateProductDto;
import com.smartCode.ecommerce.model.entity.product.ProductEntity;
import com.smartCode.ecommerce.repository.product.ProductRepository;
import com.smartCode.ecommerce.service.action.ActionService;
import com.smartCode.ecommerce.service.product.ProductService;
import com.smartCode.ecommerce.spec.product.ProductSpecification;
import com.smartCode.ecommerce.util.constants.Actions;
import com.smartCode.ecommerce.util.constants.entityTypes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ActionService actionService;
    private final ProductSpecification productSpecification;

    @Override
    @Transactional(readOnly = true)
    public ResponseProductDto getById(Integer id) {
        ProductEntity productEntity = productRepository.findById(id).orElse(null);
        return productMapper.toDto(productEntity);
    }

    @Override
    @Transactional
    public ResponseProductDto updateProduct(Integer id, UpdateProductDto updateProductDto) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("aaaaa"));
        ProductEntity entity = productMapper.toEntity(updateProductDto, productEntity);
        ProductEntity save = productRepository.save(entity);
        actionService.create(save.getId(), Actions.UPDATE, entityTypes.PRODUCT);
        return productMapper.toDto(save);
    }

    @Override
    @Transactional
    public ResponseProductDto updateProductPartially(Integer id, PartialUpdateProductDto productDto) {
        ProductEntity product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("aaa"));

        product.setName(nonNull(productDto.getName()) ? productDto.getName() : product.getName());
        product.setCategory(nonNull(productDto.getCategory()) ? productDto.getCategory() : product.getCategory());
        product.setProductionDate(nonNull(productDto.getProductionDate()) ? productDto.getProductionDate() : product.getProductionDate());
        product.setPrice(nonNull(productDto.getPrice()) ? productDto.getPrice() : product.getPrice());
        product.setDescription(nonNull(productDto.getDescription()) ? productDto.getDescription() : product.getDescription());
        ProductEntity save = productRepository.save(product);
        actionService.create(save.getId(), Actions.UPDATE, entityTypes.PRODUCT);
        return productMapper.toDto(save);
    }

    @Override
    @Transactional
    public ResponseProductDto delete(Integer id) {
        ProductEntity product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("   "));
        productRepository.delete(product);
        actionService.create(id, Actions.DELETE, entityTypes.PRODUCT);
        return productMapper.toDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseProductDto> getAll(ProductFilterSearchRequest request, PageRequest page) {
        Page<ProductEntity> products = productRepository.findAll(productSpecification.searchAndFilter(request),page);
        return products.map(productMapper::toDto);
    }

    @Override
    @Transactional
    public ResponseProductDto create(CreateProductDto createProductDto) {
        ProductEntity entity = productMapper.toEntity(createProductDto);
        ProductEntity save = productRepository.save(entity);
        actionService.create(save.getId(), Actions.CREATE, entityTypes.PRODUCT);
        return productMapper.toDto(save);
    }
}

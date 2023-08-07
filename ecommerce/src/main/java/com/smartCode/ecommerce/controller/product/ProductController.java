package com.smartCode.ecommerce.controller.product;

import com.smartCode.ecommerce.model.dto.product.CreateProductDto;
import com.smartCode.ecommerce.model.dto.product.PartialUpdateProductDto;
import com.smartCode.ecommerce.model.dto.product.ResponseProductDto;
import com.smartCode.ecommerce.model.dto.product.UpdateProductDto;
import com.smartCode.ecommerce.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Validated
public class ProductController {
    private final ProductService productService;



    @GetMapping("/{id}")
    public ResponseEntity<ResponseProductDto> getById(@PathVariable @Positive Integer id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ResponseProductDto>> getAllProducts(){
        return ResponseEntity.ok(productService.getAll());
    }



}

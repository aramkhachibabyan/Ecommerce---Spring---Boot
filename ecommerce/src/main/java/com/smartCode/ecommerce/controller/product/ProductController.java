package com.smartCode.ecommerce.controller.product;

import com.smartCode.ecommerce.model.dto.product.ProductFilterSearchRequest;
import com.smartCode.ecommerce.model.dto.product.ResponseProductDto;
import com.smartCode.ecommerce.service.product.ProductService;
import com.smartCode.ecommerce.util.page.CustomPageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

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

//    @GetMapping
//    public ResponseEntity<Page<ResponseProductDto>> getAllProducts(@RequestParam(required = false) Integer page,
//                                                                   @RequestParam(required = false) Integer size,
//                                                                   @RequestParam(name = "sort", defaultValue = "id") String sort,
//                                                                   @RequestParam(required = false, defaultValue = "DESC") Sort.Direction direction) {
//        var pageRequest = CustomPageRequest.from(page, size, Sort.by(direction, sort, "id"));
//        return ResponseEntity.ok(productService.getAll(pageRequest));
//    }

    @PostMapping("/filter")
    public ResponseEntity<Page<ResponseProductDto>> getProducts(@RequestBody(required = false) ProductFilterSearchRequest request,
                                                                @RequestParam(required = false) Integer page,
                                                                @RequestParam(required = false) Integer size,
                                                                @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                                @RequestParam(required = false, defaultValue = "DESC") Sort.Direction direction) {

        PageRequest pageRequest = CustomPageRequest.from(page, size, Sort.by(direction, sort, "id"));
        return ResponseEntity.ok(productService.getAll(request,pageRequest));
    }


}

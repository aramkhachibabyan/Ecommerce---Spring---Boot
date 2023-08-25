package com.smartCode.ecommerce.controller.order;

import com.smartCode.ecommerce.model.dto.order.OrderCreateDto;
import com.smartCode.ecommerce.model.dto.order.OrderResponseDto;
import com.smartCode.ecommerce.model.dto.pay.PaymentCreateDto;
import com.smartCode.ecommerce.payment.PaymentService;
import com.smartCode.ecommerce.service.order.OrderService;
import com.smartCode.ecommerce.util.constants.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('" + Roles.ROLE_USER + "')")
    public ResponseEntity<OrderResponseDto> createCard(@RequestBody @Valid OrderCreateDto orderCreateDto){
        return ResponseEntity.ok(orderService.createOrder(orderCreateDto));
    }
}

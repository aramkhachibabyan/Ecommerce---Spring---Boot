package com.smartCode.ecommerce.controller.payment;

import com.smartCode.ecommerce.model.dto.card.CreateCardDto;
import com.smartCode.ecommerce.model.dto.card.ResponseCardDto;
import com.smartCode.ecommerce.model.dto.pay.PaymentCreateDto;
import com.smartCode.ecommerce.payment.PaymentService;
import com.smartCode.ecommerce.service.card.CardService;
import com.smartCode.ecommerce.util.constants.Roles;
import com.smartCode.ecommerce.util.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/pay")
@RequiredArgsConstructor
@Validated
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasRole('" + Roles.ROLE_USER + "')")
    public ResponseEntity<String> createCard(@RequestBody @Valid PaymentCreateDto paymentCreateDto){
        return ResponseEntity.ok(paymentService.pay(paymentCreateDto));
    }
}

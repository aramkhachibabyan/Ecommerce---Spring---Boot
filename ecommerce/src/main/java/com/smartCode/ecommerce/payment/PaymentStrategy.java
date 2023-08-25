package com.smartCode.ecommerce.payment;

import com.smartCode.ecommerce.model.dto.pay.PaymentCreateDto;
import com.smartCode.ecommerce.util.constants.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
public interface PaymentStrategy {

    String pay(PaymentCreateDto payDto);

    PaymentType getPaymentType();

    @Autowired
    default void register(@NotNull PaymentService paymentService){
        paymentService.register(this.getPaymentType(),this);
    }
}

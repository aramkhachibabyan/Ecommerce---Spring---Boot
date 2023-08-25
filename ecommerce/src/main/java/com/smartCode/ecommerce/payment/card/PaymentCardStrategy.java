package com.smartCode.ecommerce.payment.card;

import com.smartCode.ecommerce.model.dto.pay.PaymentCreateDto;
import com.smartCode.ecommerce.payment.PaymentService;
import com.smartCode.ecommerce.payment.PaymentStrategy;
import com.smartCode.ecommerce.util.constants.PaymentType;

public class PaymentCardStrategy implements PaymentStrategy {
    private final PaymentType paymentType = PaymentType.CARD;
    @Override
    public String pay(PaymentCreateDto payDto) {
        return String.format("Pay amount: %f",payDto.getAmount());
    }

    @Override
    public PaymentType getPaymentType() {
        return paymentType;
    }

    @Override
    public void register(PaymentService paymentService) {
        PaymentStrategy.super.register(paymentService);
    }
}

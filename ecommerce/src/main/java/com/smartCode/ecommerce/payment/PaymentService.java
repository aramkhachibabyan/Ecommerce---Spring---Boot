package com.smartCode.ecommerce.payment;

import com.smartCode.ecommerce.model.dto.pay.PaymentCreateDto;
import com.smartCode.ecommerce.util.constants.PaymentType;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class PaymentService {
    private final Map<PaymentType,PaymentStrategy> payments = new EnumMap<>(PaymentType.class);

    public void register(PaymentType type, PaymentStrategy paymentStrategy){
        payments.put(type,paymentStrategy);
    }
    public String pay(PaymentCreateDto payDto){
        return payments.get(payDto.getPaymentType()).pay(payDto);
    }
}

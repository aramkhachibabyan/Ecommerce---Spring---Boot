package com.smartCode.ecommerce.model.dto.pay;

import com.smartCode.ecommerce.util.constants.PaymentType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
public class PaymentCreateDto {

    @NotNull
    private PaymentType paymentType;

    @Positive
    @NotNull
    private BigDecimal amount;

}

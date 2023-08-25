package com.smartCode.ecommerce.model.dto.order;

import com.smartCode.ecommerce.model.entity.orderItem.OrderItemEntity;
import com.smartCode.ecommerce.model.entity.user.UserEntity;
import com.smartCode.ecommerce.util.constants.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private OrderStatus status;

    private String note;

    private Integer totalCount;

    private BigDecimal totalAmount;

    private UserEntity user;

    private List<OrderItemEntity> orderItems;
}

package com.smartCode.ecommerce.service.order;

import com.smartCode.ecommerce.model.dto.order.OrderCreateDto;
import com.smartCode.ecommerce.model.dto.order.OrderResponseDto;
import com.smartCode.ecommerce.model.entity.order.OrderEntity;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {

    OrderResponseDto createOrder(OrderCreateDto orderCreateDto);
}

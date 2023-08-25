package com.smartCode.ecommerce.service.order.impl;

import com.smartCode.ecommerce.model.dto.order.OrderCreateDto;
import com.smartCode.ecommerce.model.dto.order.OrderResponseDto;
import com.smartCode.ecommerce.model.entity.basket.BasketItemEntity;
import com.smartCode.ecommerce.model.entity.order.OrderEntity;
import com.smartCode.ecommerce.model.entity.orderItem.OrderItemEntity;
import com.smartCode.ecommerce.model.entity.user.UserEntity;
import com.smartCode.ecommerce.repository.basket.BasketItemRepository;
import com.smartCode.ecommerce.repository.order.OrderRepository;
import com.smartCode.ecommerce.repository.user.UserRepository;
import com.smartCode.ecommerce.service.order.OrderService;
import com.smartCode.ecommerce.util.constants.OrderStatus;
import com.smartCode.ecommerce.util.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final BasketItemRepository basketItemRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderCreateDto orderCreateDto) {
        List<BasketItemEntity> list = basketItemRepository.findAllByUserId(CurrentUser.getId());
        OrderEntity order = new OrderEntity();
        UserEntity user = userRepository.findById(CurrentUser.getId()).get();
        order.setUser(user);
        order.setStatus(OrderStatus.ACCEPTED);
        order.setNote(orderCreateDto.getNote());
        Map<Integer, BigDecimal> map = new HashMap<>();
        for (BasketItemEntity basketItem : list) {
            map.put(basketItem.getCount(), basketItem.getProduct().getPrice());
        }
        BigDecimal totalPrice = new BigDecimal(0);
        Set<Map.Entry<Integer, BigDecimal>> entries = map.entrySet();
        for (Map.Entry<Integer, BigDecimal> entry : entries) {
            totalPrice.add(entry.getValue().multiply(new BigDecimal(entry.getKey())));
        }
        order.setTotalAmount(totalPrice);
        List<OrderItemEntity> entityList = new ArrayList<>();
        for (BasketItemEntity basketItem : list) {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setOrder(order);
            orderItemEntity.setProductId(basketItem.getProduct().getId());
            orderItemEntity.setCount(basketItem.getCount());
            orderItemEntity.setTotalPrice(basketItem.getProduct().getPrice().multiply(new BigDecimal(basketItem.getCount())));
            entityList.add(orderItemEntity);
        }
        order.setOrderItems(entityList);
        orderRepository.save(order);
        OrderResponseDto dto = new OrderResponseDto();
        dto.setNote(orderCreateDto.getNote());
        dto.setUser(user);
        dto.setOrderItems(entityList);
        dto.setStatus(OrderStatus.ACCEPTED);
        dto.setTotalAmount(totalPrice);
        return dto;

    }
}

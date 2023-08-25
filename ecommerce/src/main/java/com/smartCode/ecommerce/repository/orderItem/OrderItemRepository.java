package com.smartCode.ecommerce.repository.orderItem;

import com.smartCode.ecommerce.model.entity.orderItem.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {
}
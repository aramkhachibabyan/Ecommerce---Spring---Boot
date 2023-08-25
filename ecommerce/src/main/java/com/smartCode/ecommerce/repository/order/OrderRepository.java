package com.smartCode.ecommerce.repository.order;

import com.smartCode.ecommerce.model.entity.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
}
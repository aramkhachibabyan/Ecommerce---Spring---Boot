package com.smartCode.ecommerce.repository.basket;

import com.smartCode.ecommerce.model.entity.basket.BasketItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface BasketItemRepository extends JpaRepository<BasketItemEntity, Integer> {

    BasketItemEntity findByUserIdAndProductId(Integer userId,Integer productId);


    List<BasketItemEntity> findAllByUserId(Integer id);
}
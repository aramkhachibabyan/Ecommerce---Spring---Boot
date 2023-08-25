package com.smartCode.ecommerce.service.basketItem;

import org.springframework.transaction.annotation.Transactional;

public interface BasketItemService {

    void addOrUpdate(Integer productId);
}

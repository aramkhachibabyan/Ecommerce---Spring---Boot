package com.smartCode.ecommerce.service.basketItem.impl;

import com.smartCode.ecommerce.exceptions.ResourceNotFoundException;
import com.smartCode.ecommerce.model.entity.basket.BasketItemEntity;
import com.smartCode.ecommerce.model.entity.product.ProductEntity;
import com.smartCode.ecommerce.repository.basket.BasketItemRepository;
import com.smartCode.ecommerce.repository.product.ProductRepository;
import com.smartCode.ecommerce.repository.user.UserRepository;
import com.smartCode.ecommerce.service.basketItem.BasketItemService;
import com.smartCode.ecommerce.util.constants.Message;
import com.smartCode.ecommerce.util.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasketItemServiceImpl implements BasketItemService {
    private final BasketItemRepository basketItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public void addOrUpdate(Integer productId) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(Message.productNotFound(productId)));
        BasketItemEntity byUserIdAndProductId = basketItemRepository.findByUserIdAndProductId(CurrentUser.getId(), productId);
        if (byUserIdAndProductId == null){
            BasketItemEntity basketItem = new BasketItemEntity();
            basketItem.setCount(1);
            basketItem.setUser(userRepository.findById(CurrentUser.getId()).get());
            basketItem.setProduct(productEntity);
            basketItemRepository.save(basketItem);
        }else{
            byUserIdAndProductId.setCount(byUserIdAndProductId.getCount()+1);
            basketItemRepository.save(byUserIdAndProductId);
        }

    }
}

package com.smartCode.ecommerce.repository.token;

import com.smartCode.ecommerce.model.entity.token.TokenEntity;
import com.smartCode.ecommerce.model.entity.user.UserEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {

//    @Cacheable(value = "tokens")
    TokenEntity findTokenEntityByToken(String token);

//    @CacheEvict(value = "tokens",key = "#token")
    void deleteByUser(UserEntity user);

    TokenEntity findTokenEntityByUserId(Integer id);
}
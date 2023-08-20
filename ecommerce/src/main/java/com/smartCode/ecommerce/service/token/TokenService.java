package com.smartCode.ecommerce.service.token;

import com.smartCode.ecommerce.model.entity.token.TokenEntity;
import com.smartCode.ecommerce.model.entity.user.UserEntity;

public interface TokenService {
    TokenEntity findTokenEntityByToken(String token);

    void deleteByUserAndToken(UserEntity user,String token);
    void deleteByUser(UserEntity user);

    void saveToken(TokenEntity tokenEntity);

    TokenEntity findTokenEntityByUserId(Integer id);
}

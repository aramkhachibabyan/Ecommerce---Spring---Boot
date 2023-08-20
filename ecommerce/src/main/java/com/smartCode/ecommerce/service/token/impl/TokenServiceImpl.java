package com.smartCode.ecommerce.service.token.impl;

import com.smartCode.ecommerce.model.entity.token.TokenEntity;
import com.smartCode.ecommerce.model.entity.user.UserEntity;
import com.smartCode.ecommerce.repository.token.TokenRepository;
import com.smartCode.ecommerce.service.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;


@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    @Cacheable(value = "token")
    public TokenEntity findTokenEntityByToken(String token) {
        return tokenRepository.findTokenEntityByToken(token);
    }

    @Override
    @Transactional
    @CacheEvict(value = "token",allEntries = true)
    public void deleteByUserAndToken(UserEntity user,String token) {
         tokenRepository.deleteByUserAndToken(user,token);
    }

    @Override
    @Transactional
    public void saveToken(TokenEntity tokenEntity) {
        tokenRepository.save(tokenEntity);
    }

    @Override
    @Transactional
    @CacheEvict(value = "token",allEntries = true)
    public void deleteByUser(UserEntity user) {
        tokenRepository.deleteByUser(user);
    }

    @Override
    @Transactional
    public TokenEntity findTokenEntityByUserId(Integer id) {
        return tokenRepository.findTokenEntityByUserId(id);
    }

}

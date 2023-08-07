package com.smartCode.ecommerce.service.card.impl;

import com.smartCode.ecommerce.model.dto.card.CreateCardDto;
import com.smartCode.ecommerce.model.dto.card.ResponseCardDto;
import com.smartCode.ecommerce.service.card.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    @Transactional
    @Override
    public ResponseCardDto createCard(CreateCardDto createCardDto) {
        return new RestTemplate().postForEntity(
                "http://localhost:8081/cards", createCardDto, ResponseCardDto.class).getBody();
    }



    @Override
    @Transactional
    public void deleteCardsByUserId(Integer userId) {
        new RestTemplate().delete(String.format("http://localhost:8081/cards/users/%d", userId));
    }

    @Override
    @Transactional
    public ResponseCardDto deleteCardById(Integer id) {
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        return new RestTemplate().exchange(
                "http://localhost:8081/cards", HttpMethod.DELETE, new HttpEntity<>(new HttpHeaders()),
                ResponseCardDto.class, params).getBody();
    }
    @Override
    @Transactional(readOnly = true)
    public List<ResponseCardDto> getCardsByUserId(Integer userId) {
        return Arrays.asList(new RestTemplate().getForEntity(
                String.format("http://localhost:8081/cards/users/%d", userId), ResponseCardDto[].class).getBody());
    }
    @Override
    @Transactional()
    public ResponseCardDto getCardById(Integer id) {
        return new RestTemplate().getForEntity(String.format("http://localhost:8081/cards/%d", id),ResponseCardDto.class).getBody();
    }
}

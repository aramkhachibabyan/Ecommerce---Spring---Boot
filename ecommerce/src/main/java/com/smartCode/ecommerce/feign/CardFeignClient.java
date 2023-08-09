package com.smartCode.ecommerce.feign;

import com.smartCode.ecommerce.configuration.MicroServiceFeignConfiguration;
import com.smartCode.ecommerce.model.dto.card.CardDto;
import com.smartCode.ecommerce.model.dto.card.CreateCardDto;
import com.smartCode.ecommerce.model.dto.card.ResponseCardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "cardService", url = "http://localhost:8081/cards",configuration = MicroServiceFeignConfiguration.class)
public interface CardFeignClient {

    @PostMapping
     ResponseEntity<ResponseCardDto> createCard(@RequestBody CreateCardDto cardDto);

    @GetMapping("/users/{userId}")
     ResponseEntity<List<ResponseCardDto>> getCardsByUserId(@PathVariable Integer userId);

    @GetMapping("/{id}")
     ResponseEntity<ResponseCardDto> getCardById(@PathVariable Integer id);

    @DeleteMapping("/users/{userId}")
     ResponseEntity<List<ResponseCardDto>> deleteCardsByUserId(@PathVariable Integer userId);

    @DeleteMapping("/{id}")
     ResponseEntity<ResponseCardDto> deleteCardById(@PathVariable Integer id);

}

package com.smartCode.ecommerce.controller;

import com.smartCode.ecommerce.model.dto.card.CreateCardDto;
import com.smartCode.ecommerce.model.dto.card.ResponseCardDto;
import com.smartCode.ecommerce.service.card.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
@Validated
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<ResponseCardDto> createCard(@RequestBody @Valid CreateCardDto createCardDto){
        return ResponseEntity.ok(cardService.createCard(createCardDto));
    }

    @GetMapping("/{Id}")
    public ResponseEntity<List<ResponseCardDto>> getCardsByUserId(@PathVariable @Positive Integer Id){
        return ResponseEntity.ok(cardService.getCardByUserId(Id));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteCardsByUserId(@PathVariable @Positive Integer userId){
        cardService.deleteCardsByUserId(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<ResponseCardDto> deleteCardById(@RequestParam @Positive Integer Id){
        return ResponseEntity.ok(cardService.deleteCardById(Id));
    }
}

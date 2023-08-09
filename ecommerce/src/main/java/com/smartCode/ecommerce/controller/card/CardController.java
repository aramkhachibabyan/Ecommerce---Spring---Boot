package com.smartCode.ecommerce.controller.card;

import com.smartCode.ecommerce.model.dto.card.CreateCardDto;
import com.smartCode.ecommerce.model.dto.card.ResponseCardDto;
import com.smartCode.ecommerce.service.card.CardService;
import com.smartCode.ecommerce.util.constants.Roles;
import com.smartCode.ecommerce.util.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @PreAuthorize("hasRole('" + Roles.ROLE_USER + "')")
    public ResponseEntity<ResponseCardDto> createCard(@RequestBody @Valid CreateCardDto createCardDto){
        return ResponseEntity.ok(cardService.createCard(createCardDto));
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('" + Roles.ROLE_USER + "')")
    public ResponseEntity<List<ResponseCardDto>> getCardsByUserId(){
        return ResponseEntity.ok(cardService.getCardsByUserId(CurrentUser.getId()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('" + Roles.ROLE_USER + "')")
    public ResponseEntity<ResponseCardDto> getCardById(@PathVariable @Positive Integer id){
        return ResponseEntity.ok(cardService.getCardById(id));
    }

    @DeleteMapping("/users")
    @PreAuthorize("hasRole('" + Roles.ROLE_USER + "')")
    public ResponseEntity<Void> deleteCardsByUserId(){
        cardService.deleteCardsByUserId(CurrentUser.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('" + Roles.ROLE_USER + "')")
    public ResponseEntity<ResponseCardDto> deleteCardById(@PathVariable @Positive Integer id){
        return ResponseEntity.ok(cardService.deleteCardById(id));
    }
}

package com.smartCode.ecommerce.service.card;


import com.smartCode.ecommerce.model.dto.card.CreateCardDto;
import com.smartCode.ecommerce.model.dto.card.ResponseCardDto;

import java.util.List;

public interface CardService {

    ResponseCardDto createCard(CreateCardDto createCardDto);

    List<ResponseCardDto> getCardByUserId(Integer userId);

    void deleteCardsByUserId(Integer userId);
    ResponseCardDto deleteCardById(Integer id);

}

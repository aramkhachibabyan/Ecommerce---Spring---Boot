package com.smartCode.ecommerce.service.action.impl;

import com.smartCode.ecommerce.model.dto.action.CreateActionDto;
import com.smartCode.ecommerce.service.action.ActionService;
import com.smartCode.ecommerce.util.event.producer.CreateActionEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ActionServiceImpl  implements ActionService {
    private final CreateActionEventProducer eventProducer;

    @Override
//    @Async
    @Transactional
    public void create(Integer userId, String actionType, String entityType) {
        CreateActionDto actionDto = new CreateActionDto();
        actionDto.setUserId(userId);
        actionDto.setActionType(actionType);
        actionDto.setEntityType(entityType);
        actionDto.setActionDate(LocalDateTime.now());
        eventProducer.sendMessage(actionDto);
    }

}

package com.smartCode.ecommerce.service.notification.impl;

import com.smartCode.ecommerce.feign.NotificationFeignClient;
import com.smartCode.ecommerce.model.dto.notification.CreateNotificationDto;
import com.smartCode.ecommerce.model.dto.notification.ResponseNotificationDto;
import com.smartCode.ecommerce.model.dto.notification.VerifyNotificationDto;
import com.smartCode.ecommerce.service.action.ActionService;
import com.smartCode.ecommerce.service.notification.NotificationService;
import com.smartCode.ecommerce.util.constants.Actions;
import com.smartCode.ecommerce.util.constants.entityTypes;
import com.smartCode.ecommerce.util.event.producer.CreateNotificationEventProducer;
import com.smartCode.ecommerce.util.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationFeignClient notificationFeignClient;
    private final CreateNotificationEventProducer notificationEventProducer;
    private final ActionService actionService;

    @Override
    @Transactional
    public void create(CreateNotificationDto notificationDto) {
        notificationDto.setUserId(CurrentUser.getId());
        notificationEventProducer.sendMessage(notificationDto);
        actionService.create(notificationDto.getUserId(), Actions.CREATE, entityTypes.NOTIFICATION);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseNotificationDto> getReady() {
        return notificationFeignClient.getReady(CurrentUser.getId()).getBody();
    }
    @Override
    @Transactional(readOnly = true)
    public List<ResponseNotificationDto> getWaiting() {
        return notificationFeignClient.getSent(CurrentUser.getId()).getBody();
    }

    @Override
    @Transactional
    public void verify(String email, String code, Integer id){
        VerifyNotificationDto dto = new VerifyNotificationDto();
        dto.setUserId(id);
        dto.setEmail(email);
        dto.setContent(code);
        notificationFeignClient.verify(dto);
    }
}

package com.smartCode.ecommerce.service.notification;

import com.smartCode.ecommerce.model.dto.notification.CreateNotificationDto;
import com.smartCode.ecommerce.model.dto.notification.ResponseNotificationDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationService {
    void create(CreateNotificationDto notificationDto);

    List<ResponseNotificationDto> getReady();

    List<ResponseNotificationDto> getWaiting();


    void verify(String email, String code, Integer id);
}

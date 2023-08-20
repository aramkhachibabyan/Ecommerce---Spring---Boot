package com.smartCode.ecommerce.util.event.listener;

import com.smartCode.ecommerce.service.notification.NotificationService;
import com.smartCode.ecommerce.util.event.VerificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class VerificationEventListener {
    private final NotificationService notificationService;

    @Async
    @TransactionalEventListener
    public void handleVerificationEvent(VerificationEvent event) {
        notificationService.verify(event.getEmail(), event.getCode(), event.getUserId());
    }

}

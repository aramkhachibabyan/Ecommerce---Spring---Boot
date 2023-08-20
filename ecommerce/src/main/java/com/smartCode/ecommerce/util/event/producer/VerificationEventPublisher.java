package com.smartCode.ecommerce.util.event.producer;

import com.smartCode.ecommerce.model.entity.user.UserEntity;
import com.smartCode.ecommerce.util.event.VerificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VerificationEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishVerificationEvent(UserEntity user){
        VerificationEvent event = new VerificationEvent(this,user.getEmail(),user.getCode(), user.getId());
        applicationEventPublisher.publishEvent(event);
    }
}

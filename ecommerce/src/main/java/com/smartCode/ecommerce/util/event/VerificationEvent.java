package com.smartCode.ecommerce.util.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class VerificationEvent extends ApplicationEvent {
    private final String email;

    private final String code;

    private final Integer userId;


    public VerificationEvent(Object source, String email, String code, Integer userId) {
        super(source);
        this.email = email;
        this.code = code;
        this.userId = userId;
    }
}

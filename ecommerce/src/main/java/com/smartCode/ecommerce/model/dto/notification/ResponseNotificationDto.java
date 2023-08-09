package com.smartCode.ecommerce.model.dto.notification;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseNotificationDto extends NotificationDto{
    @Positive
    private Integer id;

    @NotNull
    private Boolean sent;

    @Positive
    private LocalDateTime creationDate;


}

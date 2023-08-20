package com.smartCode.ecommerce.model.dto.notification;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
@Getter
@Setter
public class VerifyNotificationDto {

    @NotBlank
    private String content;

    @Email
    @NotBlank
    private String email;

    @Positive
    private Integer userId;
}

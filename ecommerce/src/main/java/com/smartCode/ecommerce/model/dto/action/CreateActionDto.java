package com.smartCode.ecommerce.model.dto.action;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class CreateActionDto implements Serializable {

    private Integer userId;

    private String actionType;

    private String entityType;

    private LocalDateTime actionDate;

}

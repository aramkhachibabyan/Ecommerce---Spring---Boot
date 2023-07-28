package com.smartCode.ecommerce.model.dto.user;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ResponseUserDto extends UserDto{

    private LocalDateTime createdAt;
}

package com.smartCode.ecommerce.model.dto.user;


import com.smartCode.ecommerce.model.dto.card.ResponseCardDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ResponseUserDto extends UserDto{

    private Integer id;

    private LocalDateTime createdAt;

    private Boolean isVerified;

    private Integer age;

    List<ResponseCardDto> cards;
}

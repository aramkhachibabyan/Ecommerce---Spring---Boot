package com.smartCode.ecommerce.model.dto.user.auth;

import com.smartCode.ecommerce.model.dto.user.BaseUserDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Setter
@Getter
public class CreateUserDto extends BaseUserDto {

    @NotBlank
    @Size(min = 8)
    private String password;

}

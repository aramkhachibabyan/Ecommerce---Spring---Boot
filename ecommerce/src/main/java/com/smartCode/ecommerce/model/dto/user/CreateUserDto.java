package com.smartCode.ecommerce.model.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Setter
@Getter
public class CreateUserDto extends UserDto{

    @NotBlank
    @Size(min = 8)
    private String password;

}

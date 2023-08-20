package com.smartCode.ecommerce.model.dto.user.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserLoginDto {

    @NotBlank
    private String username;
    @NotBlank
    @Size(min = 8)
    private String password;
}

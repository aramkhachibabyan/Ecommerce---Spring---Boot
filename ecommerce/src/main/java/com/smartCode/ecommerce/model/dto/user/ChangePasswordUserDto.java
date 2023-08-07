package com.smartCode.ecommerce.model.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ChangePasswordUserDto {

    @NotBlank @Size(min = 8)
    String oldPassword;

    @NotBlank @Size(min = 8)
    String newPassword;

    @NotBlank @Size(min = 8)
    String repeatPassword;
}

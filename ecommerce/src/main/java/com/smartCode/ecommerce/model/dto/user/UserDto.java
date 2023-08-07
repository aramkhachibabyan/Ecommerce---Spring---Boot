package com.smartCode.ecommerce.model.dto.user;

import com.smartCode.ecommerce.util.constants.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
public class UserDto {

    @NotBlank
    private String name;

    @NotBlank
    private String lastname;

    private String middleName;

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "(([+374]{4}|[0]{1}))?([1-9]{2})(\\d{6})")
    private String phone;

    @NotNull
    private Gender gender;
    @NotNull
    private LocalDate dayOfBirth;
}

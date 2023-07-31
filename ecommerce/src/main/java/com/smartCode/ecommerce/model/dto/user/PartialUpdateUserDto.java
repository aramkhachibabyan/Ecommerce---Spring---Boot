package com.smartCode.ecommerce.model.dto.user;

import com.smartCode.ecommerce.util.constants.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
public class PartialUpdateUserDto {
    @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
    private String name;

    @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
    private String lastname;

    @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
    private String middleName;

    @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
    private String username;

    @Email
    private String email;

    @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
    private String phone;

    //    @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
    private Gender gender;

    @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
    private LocalDate dayOfBirth;
}

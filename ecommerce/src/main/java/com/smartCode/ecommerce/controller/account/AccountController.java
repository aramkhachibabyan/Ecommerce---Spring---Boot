package com.smartCode.ecommerce.controller.account;

import com.smartCode.ecommerce.model.dto.user.ResponseUserDto;
import com.smartCode.ecommerce.model.dto.user.auth.CreateUserDto;
import com.smartCode.ecommerce.model.dto.user.auth.UserAuthDto;
import com.smartCode.ecommerce.model.dto.user.auth.UserLoginDto;
import com.smartCode.ecommerce.model.dto.user.auth.VerificationDto;
import com.smartCode.ecommerce.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
@Validated
public class AccountController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseUserDto> register(@RequestBody @Valid CreateUserDto user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/verify")
    public ResponseEntity<ResponseUserDto> verify(@RequestBody @Valid VerificationDto dto) {
        return ResponseEntity.ok(userService.verify(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuthDto> loginUser(@RequestBody @Valid UserLoginDto dto) {
        return ResponseEntity.ok(userService.login(dto));
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") @NotBlank String token) {
        userService.logout(token);
        return ResponseEntity.ok().build();
    }





}

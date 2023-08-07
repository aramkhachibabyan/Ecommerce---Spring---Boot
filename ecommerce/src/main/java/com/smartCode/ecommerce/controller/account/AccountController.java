package com.smartCode.ecommerce.controller.account;

import com.smartCode.ecommerce.model.dto.user.CreateUserDto;
import com.smartCode.ecommerce.model.dto.user.PartialUpdateUserDto;
import com.smartCode.ecommerce.model.dto.user.ResponseUserDto;
import com.smartCode.ecommerce.model.dto.user.UpdateUserDto;
import com.smartCode.ecommerce.model.dto.user.UserAuthDto;
import com.smartCode.ecommerce.service.user.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class AccountController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseUserDto> register(@RequestBody @Valid CreateUserDto user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/verify/{id}")
    public ResponseEntity<ResponseUserDto> verify(@PathVariable @Positive Integer id,
                                                  @RequestParam @NotBlank @Size(min = 6, max = 6) String code) {
        return ResponseEntity.ok(userService.verify(id, code));
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuthDto> loginUser(@RequestParam @NotBlank String username,
                                                 @RequestParam @NotBlank @Size(min = 8) String password) {
        return ResponseEntity.ok(userService.login(username, password));
    }

    @GetMapping("/aaa/logout")
    public ResponseEntity<Void> logout() {
        userService.logout();
        return ResponseEntity.ok().build();
    }





}

package com.smartCode.ecommerce.controller;

import com.smartCode.ecommerce.model.dto.user.CreateUserDto;
import com.smartCode.ecommerce.model.dto.user.PartialUpdateUserDto;
import com.smartCode.ecommerce.model.dto.user.ResponseUserDto;
import com.smartCode.ecommerce.model.dto.user.UpdateUserDto;
import com.smartCode.ecommerce.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class AccountController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResponseUserDto> create(@RequestBody @Valid CreateUserDto user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDto> getById(@PathVariable @Positive Integer id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping( "/{id}")
    public ResponseEntity<ResponseUserDto> verify(@PathVariable @Positive Integer id,
                                                  @RequestParam @NotBlank @Size(min = 6, max = 6) String code) {
        return ResponseEntity.ok(userService.verify(id, code));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ResponseUserDto> deleteUser(@PathVariable @Positive Integer id, @RequestParam @NotBlank @Size(min = 8) String password) {
        return ResponseEntity.ok(userService.delete(id, password));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<ResponseUserDto> loginUser(@RequestParam @NotBlank @Email String email,
                                                     @RequestParam @NotBlank @Size(min = 8) String password) {
        return ResponseEntity.ok(userService.login(email, password));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<ResponseUserDto> updateUserPartially(@PathVariable @Positive Integer id,
                                                               @RequestBody @Valid PartialUpdateUserDto user) {
        return ResponseEntity.ok(userService.updatePartially(id, user));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ResponseUserDto> updateUser(@PathVariable @Positive Integer id, @RequestBody @Valid UpdateUserDto user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @PutMapping(path = "/changePassword/{id}")
    public ResponseEntity<ResponseUserDto> changePassword(@RequestParam @NotBlank @Size(min = 8) String oldPassword,
                                                          @RequestParam @NotBlank @Size(min = 8) String newPassword,
                                                          @RequestParam @NotBlank @Size(min = 8) String repeatPassword,
                                                          @PathVariable @Positive Integer id) {
        return ResponseEntity.ok(userService.changePassword(id,oldPassword, newPassword, repeatPassword));
    }

    @GetMapping
    public ResponseEntity<List<ResponseUserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


}

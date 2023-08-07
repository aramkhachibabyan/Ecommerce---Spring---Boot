package com.smartCode.ecommerce.controller.account;

import com.smartCode.ecommerce.model.dto.user.ChangePasswordUserDto;
import com.smartCode.ecommerce.model.dto.user.PartialUpdateUserDto;
import com.smartCode.ecommerce.model.dto.user.ResponseUserDto;
import com.smartCode.ecommerce.model.dto.user.UpdateUserDto;
import com.smartCode.ecommerce.service.user.UserService;
import com.smartCode.ecommerce.util.constants.Roles;
import com.smartCode.ecommerce.util.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private  final UserService userService;
    @DeleteMapping
    @PreAuthorize("hasRole('" + Roles.ROLE_USER + "')")
    public ResponseEntity<ResponseUserDto> deleteUser() {
        return ResponseEntity.ok(userService.delete(CurrentUser.getId()));
    }

    @PatchMapping
    @PreAuthorize("hasRole('" + Roles.ROLE_USER + "')")
    public ResponseEntity<ResponseUserDto> updateUserPartially(@RequestBody @Valid PartialUpdateUserDto user) {
        return ResponseEntity.ok(userService.updatePartially(CurrentUser.getId(), user));
    }

    @PutMapping
    @PreAuthorize("hasRole('" + Roles.ROLE_USER + "')")
    public ResponseEntity<ResponseUserDto> updateUser(@RequestBody @Valid UpdateUserDto user) {
        return ResponseEntity.ok(userService.updateUser(CurrentUser.getId(), user));
    }

    @PutMapping(path = "/changePassword")
    @PreAuthorize("hasRole('" + Roles.ROLE_USER + "')")
    public ResponseEntity<ResponseUserDto> changePassword(@RequestBody @Valid ChangePasswordUserDto dto) {
        return ResponseEntity.ok(userService.changePassword(CurrentUser.getId(),dto));
    }
}

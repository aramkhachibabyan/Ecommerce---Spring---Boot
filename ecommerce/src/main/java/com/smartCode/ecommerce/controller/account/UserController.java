package com.smartCode.ecommerce.controller.account;

import com.smartCode.ecommerce.model.dto.user.ResponseUserDto;
import com.smartCode.ecommerce.model.dto.user.update.ChangePasswordUserDto;
import com.smartCode.ecommerce.model.dto.user.update.PartialUpdateUserDto;
import com.smartCode.ecommerce.model.dto.user.update.UpdateBaseUserDto;
import com.smartCode.ecommerce.service.user.UserService;
import com.smartCode.ecommerce.util.constants.Roles;
import com.smartCode.ecommerce.util.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public ResponseEntity<ResponseUserDto> updateUser(@RequestBody @Valid UpdateBaseUserDto user) {
        return ResponseEntity.ok(userService.updateUser(CurrentUser.getId(), user));
    }

    @PutMapping(path = "/changePassword")
    @PreAuthorize("hasRole('" + Roles.ROLE_USER + "')")
    public ResponseEntity<ResponseUserDto> changePassword(@RequestBody @Valid ChangePasswordUserDto dto) {
        return ResponseEntity.ok(userService.changePassword(CurrentUser.getId(),dto));
    }
}

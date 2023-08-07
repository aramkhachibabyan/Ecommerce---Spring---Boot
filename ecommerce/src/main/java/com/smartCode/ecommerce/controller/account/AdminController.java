package com.smartCode.ecommerce.controller.account;

import com.smartCode.ecommerce.model.dto.user.ResponseUserDto;
import com.smartCode.ecommerce.service.user.UserService;
import com.smartCode.ecommerce.util.constants.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {
    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('" + Roles.ROLE_ADMIN + "')")
    public ResponseEntity<ResponseUserDto> getById(@PathVariable @Positive Integer id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping
//    @PreAuthorize("hasRole('" + Roles.ROLE_ADMIN + "')")
    public ResponseEntity<List<ResponseUserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}

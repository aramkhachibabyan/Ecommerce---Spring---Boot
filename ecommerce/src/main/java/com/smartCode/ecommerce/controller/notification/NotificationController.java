package com.smartCode.ecommerce.controller.notification;

import com.smartCode.ecommerce.model.dto.notification.CreateNotificationDto;
import com.smartCode.ecommerce.model.dto.notification.ResponseNotificationDto;
import com.smartCode.ecommerce.service.notification.NotificationService;
import com.smartCode.ecommerce.util.constants.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notify")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    @PreAuthorize("hasRole('" + Roles.ROLE_USER + "')")
    public ResponseEntity<ResponseNotificationDto> create(@RequestBody @Valid CreateNotificationDto dto) {
        return ResponseEntity.ok(notificationService.create(dto));
    }

    @GetMapping("/ready")
    @PreAuthorize("hasRole('" + Roles.ROLE_USER + "')")
    public ResponseEntity<List<ResponseNotificationDto>> getReady(){
        return ResponseEntity.ok(notificationService.getReady());
    }

    @GetMapping("/waiting")
    @PreAuthorize("hasRole('" + Roles.ROLE_USER + "')")
    public ResponseEntity<List<ResponseNotificationDto>> getSent(){
        return ResponseEntity.ok(notificationService.getWaiting());
    }


}
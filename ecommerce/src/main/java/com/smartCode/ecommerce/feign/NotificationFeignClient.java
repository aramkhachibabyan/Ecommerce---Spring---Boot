package com.smartCode.ecommerce.feign;

import com.smartCode.ecommerce.configuration.MicroServiceFeignConfiguration;
import com.smartCode.ecommerce.model.dto.notification.CreateNotificationDto;
import com.smartCode.ecommerce.model.dto.notification.ResponseNotificationDto;
import com.smartCode.ecommerce.model.dto.notification.VerifyNotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "notificationService", url = "http://localhost:8082/notify", configuration = MicroServiceFeignConfiguration.class)
public interface NotificationFeignClient {

    @PostMapping
    ResponseEntity<ResponseNotificationDto> create(@RequestBody CreateNotificationDto dto);

    @GetMapping("/ready")
    ResponseEntity<List<ResponseNotificationDto>> getReady(@RequestParam Integer userId);

    @GetMapping("/waiting")
    ResponseEntity<List<ResponseNotificationDto>> getSent(@RequestParam Integer userId);

    @PostMapping("/verify")
    ResponseEntity<Void> verify(@RequestBody VerifyNotificationDto dto);


}

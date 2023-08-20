package com.smartCode.ecommerce.util.event.producer;

import com.smartCode.ecommerce.model.dto.notification.CreateNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateNotificationEventProducer {

    private final KafkaTemplate<String, CreateNotificationDto> notifyKafkaTemplate;

    @Value("${kafka.notification.topic.name}")
    private String topicName;

    public void sendMessage(CreateNotificationDto dto) {
        notifyKafkaTemplate.send(topicName, dto);
    }

}

package com.smartCode.ecommerce.util.event.producer;

import com.smartCode.ecommerce.model.dto.action.CreateActionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateActionEventProducer {

    private final KafkaTemplate<String, CreateActionDto> actionKafkaTemplate;

    @Value("${kafka.activity.topic.name}")
    private String topicName;

    public void sendMessage(CreateActionDto dto) {
        actionKafkaTemplate.send(topicName, dto);
    }
}

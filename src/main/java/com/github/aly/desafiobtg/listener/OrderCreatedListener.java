package com.github.aly.desafiobtg.listener;

import com.github.aly.desafiobtg.config.RabbitMQConfig;
import com.github.aly.desafiobtg.payload.OrderCreatedEvent;
import com.github.aly.desafiobtg.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class OrderCreatedListener {
    private final OrderServiceImpl service;

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE_BTG)
    public void listener(Message<OrderCreatedEvent> message) {
        log.info("Message consumed >>> {}", message);
        service.create(message.getPayload());
    }
}

package com.github.aly.desafiobtg.service;

import com.github.aly.desafiobtg.payload.OrderCreatedEvent;
import com.github.aly.desafiobtg.payload.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface OrderService {
    void create(OrderCreatedEvent event);
    Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest);
    Long findTotalOrdersByCustomerId(Long customerId);
}

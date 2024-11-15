package com.github.aly.desafiobtg.util;

import com.github.aly.desafiobtg.entity.OrderItem;
import com.github.aly.desafiobtg.payload.OrderCreatedEvent;
import com.github.aly.desafiobtg.payload.OrderItemEvent;

import java.math.BigDecimal;
import java.util.List;

public class BtgUtils {
    public static BigDecimal getTotal(List<OrderItemEvent> orderItemEvent) {
        return orderItemEvent.stream()
                .map(item -> item.preco().multiply(BigDecimal.valueOf(item.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public static List<OrderItem> getAndMapperOrderItems(OrderCreatedEvent event) {
        return event.itens()
                .stream()
                .map(orderItemEvent -> OrderItem.builder()
                        .product(orderItemEvent.produto())
                        .quantity(orderItemEvent.quantidade())
                        .price(orderItemEvent.preco())
                        .build()).toList();
    }
}

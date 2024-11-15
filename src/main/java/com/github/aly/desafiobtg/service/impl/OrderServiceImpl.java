package com.github.aly.desafiobtg.service.impl;

import com.github.aly.desafiobtg.entity.Order;
import com.github.aly.desafiobtg.payload.OrderCreatedEvent;
import com.github.aly.desafiobtg.payload.OrderResponse;
import com.github.aly.desafiobtg.repository.OrderRepository;
import com.github.aly.desafiobtg.service.OrderService;
import com.github.aly.desafiobtg.util.BtgUtils;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final MongoTemplate mongoTemplate;

    @Override
    public void create(OrderCreatedEvent event) {
        var entity = Order.builder()
                .id(event.codigoPedido())
                .customerId(event.codigoCliente())
                .total(BtgUtils.getTotal(event.itens()))
                .items(BtgUtils.getAndMapperOrderItems(event)).build();

        repository.save(entity);
    }

    @Override
    public Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest) {
        return repository.findAllByCustomerId(customerId, pageRequest)
                .map(order -> OrderResponse.builder()
                        .id(order.getId())
                        .customerId(order.getCustomerId())
                        .total(order.getTotal())
                        .build());
    }

    @Override
    public Long findTotalOrdersByCustomerId(Long customerId) {
        var agg = newAggregation(
                match(Criteria.where("customerId").is(customerId)),
                group().count().as("total"),
                project("total").andExclude("_id")
        );

        return  Long.valueOf(mongoTemplate.aggregate(agg, "orders", Document.class).getUniqueMappedResult().get("total").toString());
    }
}

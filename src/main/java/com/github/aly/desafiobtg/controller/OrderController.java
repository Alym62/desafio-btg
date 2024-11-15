package com.github.aly.desafiobtg.controller;

import com.github.aly.desafiobtg.payload.BaseResponse;
import com.github.aly.desafiobtg.payload.OrderResponse;
import com.github.aly.desafiobtg.payload.PaginationResponse;
import com.github.aly.desafiobtg.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceImpl service;

    @GetMapping("/costumer/{customerId}/orders")
    public ResponseEntity<BaseResponse<OrderResponse>> listOrders(@PathVariable("customerId") Long customerId,
                                                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                                                  @RequestParam(name = "perPage", defaultValue = "10") int perPage) {
        var body = service.findAllByCustomerId(customerId, PageRequest.of(page, perPage));
        return ResponseEntity.ok().body(new BaseResponse<>(
                body.getContent(),
                PaginationResponse.builder()
                        .page(body.getNumber())
                        .perPage(body.getSize())
                        .totalElements(body.getTotalElements())
                        .totalPages(body.getTotalElements())
                        .build()
        ));
    }

    @GetMapping("/costumer/{customerId}/totalOrders")
    public ResponseEntity<Long> totalOrders(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok().body(service.findTotalOrdersByCustomerId(customerId));
    }
}

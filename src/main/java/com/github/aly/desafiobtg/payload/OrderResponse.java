package com.github.aly.desafiobtg.payload;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderResponse(
        Long id,
        Long customerId,
        BigDecimal total
) {
}

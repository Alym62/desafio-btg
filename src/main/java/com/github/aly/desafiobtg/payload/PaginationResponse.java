package com.github.aly.desafiobtg.payload;

import lombok.Builder;

@Builder
public record PaginationResponse(
        int page,
        int perPage,
        long totalElements,
        long totalPages
) {
}

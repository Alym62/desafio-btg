package com.github.aly.desafiobtg.payload;

import java.util.List;

public record BaseResponse<T>(List<T> list, PaginationResponse paginationResponse) {
}

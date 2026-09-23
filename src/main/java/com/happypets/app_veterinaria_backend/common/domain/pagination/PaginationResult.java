package com.happypets.app_veterinaria_backend.common.domain.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Principal pagination result class response
 */
@Data
@AllArgsConstructor
public class PaginationResult<T> {

    private List<T> content;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
}

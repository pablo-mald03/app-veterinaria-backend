package com.happypets.app_veterinaria_backend.common.domain.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal pagination class for the different types of filters
 */

@Data
@AllArgsConstructor
public class PaginationQuery {
    private int page;
    private int size;
    private String sortBy;
    private String direction;

}

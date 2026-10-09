package com.happypets.app_veterinaria_backend.room.application.query.getAll;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.role.application.query.getAll.GetAllRoleResponse;
import com.happypets.app_veterinaria_backend.room.domain.filter.RoomFilter;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal get all rooms request class
 *
 */
@Data
@AllArgsConstructor
public class GetAllRoomsRequest implements Request<GetAllRoomsResponse>, AuditableRequest {
    private RoomFilter filter;
    private PaginationQuery paginationQuery;

    /**
     * Log module
     */
    @Override
    public String getModule() {
        return "HABITACIONES";
    }

    /**
     * Log action
     */
    @Override
    public String getAction() {
        return "CONSULTAR HABITACIONES";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        GetAllRoomsResponse result = (GetAllRoomsResponse) response;
        return "Se obtuvieron '" + result.getResult().getContent().size() + "' habitaciones";
    }
}
package com.happypets.app_veterinaria_backend.user.application.query;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal get all users response
 *
 */
@Data
@AllArgsConstructor
public class GetAllUsersResponse {

    private PaginationResult<User> allUsers;

}

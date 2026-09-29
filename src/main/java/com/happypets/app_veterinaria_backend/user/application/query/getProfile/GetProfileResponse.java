package com.happypets.app_veterinaria_backend.user.application.query.getProfile;

import com.happypets.app_veterinaria_backend.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get profile response class
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProfileResponse {
    private User user;
}

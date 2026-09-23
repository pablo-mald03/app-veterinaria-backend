package com.happypets.app_veterinaria_backend.user.application.command.change;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Disable user class request
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserStatusRequest implements Request<ChangeUserStatusResponse> {
    private Long userId;
    private boolean status;
}
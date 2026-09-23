package com.happypets.app_veterinaria_backend.user.application.command.change;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Disable user class response
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserStatusResponse {
    private Long userId;
    private boolean status;
}
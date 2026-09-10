package com.happypets.app_veterinaria_backend.user.application.command.recoverPassword;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal recover password request class
 *
 */
@Data
@AllArgsConstructor
public class RecoverPasswordRequest implements Request<Void> {

    private String dpi;
    private String email;
    private String password;
    private String confirmationPassword;
}

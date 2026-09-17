package com.happypets.app_veterinaria_backend.permissions.domain.exeptions;


import com.happypets.app_veterinaria_backend.common.domain.exception.ResourceNotFoundException;

/**
 * Principal exeption when the resource was not found
 *
 */
public class InvalidPermissionIdsException extends ResourceNotFoundException {
    public InvalidPermissionIdsException(String message) {
        super(message);
    }
}

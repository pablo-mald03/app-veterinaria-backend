package com.happypets.app_veterinaria_backend.common.domain.auditable;

/**
 * Principal request for auditable use case for logs
 *
 */
public interface AuditableRequest {
    String getModule();

    String getAction();

    String getDetail(Object response);
}
package com.happypets.app_veterinaria_backend.category.application.command.patch;


import com.happypets.app_veterinaria_backend.category.domain.exception.CategoryInUseException;
import com.happypets.app_veterinaria_backend.category.domain.exception.CategoryNotFoundException;
import com.happypets.app_veterinaria_backend.category.domain.port.CategoryRepositoryPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler class to change status for a category
 *
 */
@Service
@RequiredArgsConstructor
public class ChangeStatusCategoryHandler implements RequestHandler<ChangeStatusCategoryRequest, Void> {

    private final CategoryRepositoryPort categoryRepositoryPort;
    private final ProductRepositoryPort productRepositoryPort;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Void handle(ChangeStatusCategoryRequest request) {

        if (!request.getStatus()) {
            if (categoryRepositoryPort.findById(request.getId()).isEmpty()) {
                throw new CategoryNotFoundException("Categoría no encontrada con ID: " + request.getId());
            }

            boolean hasAssociatedProducts = productRepositoryPort.existsByCategoryId(request.getId());
            if (hasAssociatedProducts) {
                throw new CategoryInUseException(
                        "No se puede desactivar la categoría con ID " + request.getId() +
                                " porque tiene productos asociados."
                );
            }
        }

        categoryRepositoryPort.changeStatus(request.getId(), request.getStatus());
        return null;
    }

    @Override
    public Class<ChangeStatusCategoryRequest> getRequestType() {
        return ChangeStatusCategoryRequest.class;
    }
}
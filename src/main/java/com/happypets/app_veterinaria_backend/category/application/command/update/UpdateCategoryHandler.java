package com.happypets.app_veterinaria_backend.category.application.command.update;

import com.happypets.app_veterinaria_backend.category.domain.entity.Category;
import com.happypets.app_veterinaria_backend.category.domain.exception.CategoryAlreadyExistException;
import com.happypets.app_veterinaria_backend.category.domain.exception.CategoryNotFoundException;
import com.happypets.app_veterinaria_backend.category.domain.port.CategoryRepositoryPort;
import com.happypets.app_veterinaria_backend.category.infrastructure.service.SlugifyCategoryService;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler class to update a new category
 *
 */
@Service
@RequiredArgsConstructor
public class UpdateCategoryHandler implements RequestHandler<UpdateCategoryRequest, UpdateCategoryResponse> {

    private final CategoryRepositoryPort categoryRepositoryPort;
    private final SlugifyCategoryService slugifyCategoryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UpdateCategoryResponse handle(UpdateCategoryRequest request) {

        Category existingCategory = categoryRepositoryPort.findById(request.getId())
                .orElseThrow(() -> new CategoryNotFoundException("Categoría no encontrada con ID: " + request.getId()));

        String generatedAlias = slugifyCategoryService.generateAlias(request.getName());

        if (!existingCategory.getAlias().equalsIgnoreCase(generatedAlias)
                && categoryRepositoryPort.existsByAlias(generatedAlias)) {
            throw new CategoryAlreadyExistException("El nombre de la categoría: '" + request.getName() + "' ya existe");
        }

        existingCategory.setName(request.getName());
        existingCategory.setAlias(generatedAlias);
        existingCategory.setDescription(request.getDescription());

        Category updatedCategory = categoryRepositoryPort.update(existingCategory);

        return new UpdateCategoryResponse(updatedCategory.getName());
    }

    @Override
    public Class<UpdateCategoryRequest> getRequestType() {
        return UpdateCategoryRequest.class;
    }
}
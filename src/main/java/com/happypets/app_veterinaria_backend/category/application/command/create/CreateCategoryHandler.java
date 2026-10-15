package com.happypets.app_veterinaria_backend.category.application.command.create;

import com.happypets.app_veterinaria_backend.category.domain.entity.Category;
import com.happypets.app_veterinaria_backend.category.domain.exception.CategoryAlreadyExistException;
import com.happypets.app_veterinaria_backend.category.domain.port.CategoryRepositoryPort;
import com.happypets.app_veterinaria_backend.category.infrastructure.service.SlugifyCategoryService;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler class to create a new category
 *
 */
@Service
@RequiredArgsConstructor
public class CreateCategoryHandler implements RequestHandler<CreateCategoryRequest, CreateCategoryResponse> {

    //Attributes
    private final CategoryRepositoryPort categoryRepositoryPort;

    private final SlugifyCategoryService slugifyCategoryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreateCategoryResponse handle(CreateCategoryRequest request) {

        String generatedAlias = slugifyCategoryService.generateAlias(request.getName());

        if (categoryRepositoryPort.existsByAlias(generatedAlias)) {
            throw new CategoryAlreadyExistException("El nombre de la categoria: '" + request.getName() + "' ya existe");
        }

        Category category = Category.builder().name(request.getName()).alias(generatedAlias).description(request.getDescription()).build();

        Category saved = categoryRepositoryPort.save(category);

        return new CreateCategoryResponse(saved.getName());
    }

    @Override
    public Class<CreateCategoryRequest> getRequestType() {
        return CreateCategoryRequest.class;
    }
}
package com.happypets.app_veterinaria_backend.category.application.query.getById;


import com.happypets.app_veterinaria_backend.category.domain.entity.Category;
import com.happypets.app_veterinaria_backend.category.domain.exception.CategoryNotFoundException;
import com.happypets.app_veterinaria_backend.category.domain.port.CategoryRepositoryPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetCategoryByIdHandler implements RequestHandler<GetCategoryByIdRequest, GetCategoryByIdResponse> {

    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GetCategoryByIdResponse handle(GetCategoryByIdRequest request) {

        Category category = categoryRepositoryPort.findById(request.getId())
                .orElseThrow(() -> new CategoryNotFoundException("Categoría no encontrada con ID: " + request.getId()));

        return new GetCategoryByIdResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.isStatus()
        );
    }

    @Override
    public Class<GetCategoryByIdRequest> getRequestType() {
        return GetCategoryByIdRequest.class;
    }
}
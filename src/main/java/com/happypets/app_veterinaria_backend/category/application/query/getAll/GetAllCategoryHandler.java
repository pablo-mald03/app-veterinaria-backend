package com.happypets.app_veterinaria_backend.category.application.query.getAll;


import com.happypets.app_veterinaria_backend.category.domain.entity.Category;
import com.happypets.app_veterinaria_backend.category.domain.filter.CategoryFilter;
import com.happypets.app_veterinaria_backend.category.domain.port.CategoryRepositoryPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Principal recover get all categories handler class
 *
 */
@Service
@RequiredArgsConstructor
public class GetAllCategoryHandler implements RequestHandler<GetAllCategoryRequest, GetAllCategoryResponse> {

    //Attributes
    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public GetAllCategoryResponse handle(GetAllCategoryRequest request) {
        CategoryFilter filter = CategoryFilter.builder()
                .status(request.getStatus()).build();

        PaginationResult<Category> categories = categoryRepositoryPort.findAll(filter, request.getPaginationQuery());

        return new GetAllCategoryResponse(categories);
    }


    @Override
    public Class<GetAllCategoryRequest> getRequestType() {
        return GetAllCategoryRequest.class;
    }
}
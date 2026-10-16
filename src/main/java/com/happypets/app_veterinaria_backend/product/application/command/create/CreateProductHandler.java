package com.happypets.app_veterinaria_backend.product.application.command.create;


import com.happypets.app_veterinaria_backend.category.domain.entity.Category;
import com.happypets.app_veterinaria_backend.category.domain.exception.CategoryDisabledException;
import com.happypets.app_veterinaria_backend.category.domain.exception.CategoryNotFoundException;
import com.happypets.app_veterinaria_backend.category.domain.port.CategoryRepositoryPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.product.domain.entity.Product;
import com.happypets.app_veterinaria_backend.product.domain.exception.ProductSkuAlreadyExistsException;
import com.happypets.app_veterinaria_backend.product.domain.port.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Principal handler class to create product
 *
 */
@Service
@RequiredArgsConstructor
public class CreateProductHandler implements RequestHandler<CreateProductRequest, CreateProductResponse> {

    private final ProductRepositoryPort productRepository;
    private final CategoryRepositoryPort categoryRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreateProductResponse handle(CreateProductRequest request) {

        if (productRepository.existSku(request.getSku())) {
            throw new ProductSkuAlreadyExistsException("El SKU " + request.getSku() + " ya está en uso.");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Categoría no encontrada"));

        if (!category.isStatus()) {
            throw new CategoryDisabledException("No se puede asignar una categoría inactiva a un producto nuevo.");
        }

        Product product = Product.builder()
                .sku(request.getSku())
                .name(request.getName())
                .description(request.getDescription())
                .brand(request.getBrand())
                .minimumStock(request.getMinimumStock())
                .price(request.getPrice())
                .categoryId(request.getCategoryId())
                .profitPercentage(request.getProfitPercentage())
                .provider(request.getProvider())
                .status(true)
                .build();
        Product savedProduct = productRepository.save(product);

        return new CreateProductResponse(savedProduct.getSku());
    }

    @Override
    public Class<CreateProductRequest> getRequestType() {
        return CreateProductRequest.class;
    }
}
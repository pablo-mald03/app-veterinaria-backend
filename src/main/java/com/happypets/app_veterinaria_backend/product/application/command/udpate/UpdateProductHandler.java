package com.happypets.app_veterinaria_backend.product.application.command.udpate;


import com.happypets.app_veterinaria_backend.category.domain.entity.Category;
import com.happypets.app_veterinaria_backend.category.domain.exception.CategoryDisabledException;
import com.happypets.app_veterinaria_backend.category.domain.exception.CategoryNotFoundException;
import com.happypets.app_veterinaria_backend.category.domain.port.CategoryRepositoryPort;
import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.product.domain.entity.Product;
import com.happypets.app_veterinaria_backend.product.domain.entity.ProductDetail;
import com.happypets.app_veterinaria_backend.product.domain.exception.ProductNotFountException;
import com.happypets.app_veterinaria_backend.product.domain.exception.ProductSkuAlreadyExistsException;
import com.happypets.app_veterinaria_backend.product.domain.port.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Principal handler class to update product
 *
 */
@Service
@RequiredArgsConstructor
public class UpdateProductHandler implements RequestHandler<UpdateProductRequest, UpdateProductResponse> {
    //Attributes
    private final ProductRepositoryPort productRepository;
    private final CategoryRepositoryPort categoryRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UpdateProductResponse handle(UpdateProductRequest request) {

        ProductDetail productExisting = productRepository.findById(request.getId())
                .orElseThrow(() -> new ProductNotFountException("El producto a modificar no existe"));

        String trimmedSku = request.getSku().trim();
        if (!productExisting.getProduct().getSku().equals(trimmedSku)) {
            if (productRepository.existSku(request.getSku())) {
                throw new ProductSkuAlreadyExistsException("El SKU " + request.getSku() + " ya está en uso.");
            }
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Categoría no encontrada"));

        if (!category.isStatus()) {
            throw new CategoryDisabledException("No se puede asignar una categoría inactiva a un producto nuevo.");
        }


        Product productDomain = Product.builder()
                .id(request.getId())
                .sku(request.getSku())
                .name(request.getName())
                .description(request.getDescription())
                .brand(request.getBrand())
                .minimumStock(request.getMinimumStock())
                .price(request.getPrice())
                .profitPercentage(request.getProfitPercentage())
                .categoryId(request.getCategoryId())
                .provider(request.getProvider())
                .build();

        Product updatedProduct = productRepository.update(productDomain);

        return new UpdateProductResponse(updatedProduct.getSku());
    }

    @Override
    public Class<UpdateProductRequest> getRequestType() {
        return UpdateProductRequest.class;
    }
}
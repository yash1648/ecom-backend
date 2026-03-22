package com.grim.product.mapper;

import com.grim.product.dto.ProductPurchaseResponse;
import com.grim.product.dto.ProductRequest;
import com.grim.product.dto.ProductResponse;
import com.grim.product.model.Category;
import com.grim.product.model.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest product) {
        return Product.builder()
                .id(product.id())
                .description(product.description())
                .price(product.price())
                .name(product.name())
                .availableQuantity(product.availableQuantity())
                .category(Category.builder()
                        .id(product.id())
                        .build()
                )
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
            return new ProductResponse(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getAvailableQuantity(),
                    product.getPrice(),
                    product.getCategory().getId(),
                    product.getCategory().getName(),
                    product.getCategory().getDescription()
            );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, @NotNull(message = "Quantity cannot be null") double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}

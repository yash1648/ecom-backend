package com.grim.product.repository;

import com.grim.product.model.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByIdInOrderById(List<@NotNull(message = "Product id cannot be null") Integer> productIds);
}

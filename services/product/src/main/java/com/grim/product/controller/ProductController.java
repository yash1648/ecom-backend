package com.grim.product.controller;


import com.grim.product.dto.ProductPurchaseRequest;
import com.grim.product.dto.ProductPurchaseResponse;
import com.grim.product.dto.ProductRequest;
import com.grim.product.dto.ProductResponse;
import com.grim.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Integer> createProduct(@RequestBody ProductRequest product) {

        return ResponseEntity.ok(productService.createProduct(product));

    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProduct
            (@RequestBody List<ProductPurchaseRequest> products) {

        return ResponseEntity.ok(productService.purchaseProduct(products));

    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> findById
            (@PathVariable("product-id") Integer productId) {
        return ResponseEntity.ok(productService.findById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }

}

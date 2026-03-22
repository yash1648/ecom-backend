package com.grim.product.service;

import com.grim.product.dto.ProductPurchaseRequest;
import com.grim.product.dto.ProductPurchaseResponse;
import com.grim.product.dto.ProductRequest;
import com.grim.product.dto.ProductResponse;
import com.grim.product.exception.ProductPurchaseException;
import com.grim.product.mapper.ProductMapper;
import com.grim.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public Integer createProduct(ProductRequest product) {

        var newproduct = productMapper.toProduct(product);
        return productRepository.save(newproduct).getId();

    }

    public List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> products) {
            var productIds=products.stream()
                    .map(ProductPurchaseRequest::productId)
                    .toList();

            var storedProducts=productRepository.findAllByIdInOrderById(productIds);

            if(productIds.size()!=storedProducts.size()){
                throw new ProductPurchaseException("One or more product does not exists");
            }
            var storedRequest=products.stream()
                    .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                    .toList();

            var purchasedProducts=new ArrayList<ProductPurchaseResponse>();
            for(int i=0;i<storedProducts.size();i++){
                var product=storedProducts.get(i);
                var productRequest=storedRequest.get(i);
                if(product.getAvailableQuantity()<productRequest.quantity()){
                    throw new ProductPurchaseException("Product with id %d does not have enough quantity".formatted(product.getId()));
                }

                var newAvailableQuantity=product.getAvailableQuantity()-productRequest.quantity();
                product.setAvailableQuantity(newAvailableQuantity);
                productRepository.save(product);
                purchasedProducts.add(productMapper.toProductPurchaseResponse(product,productRequest.quantity()));
            }
            return purchasedProducts;

    }

    public ProductResponse findById(Integer productId) {
        return productRepository.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow(()->new EntityNotFoundException("Product with id %d not found".formatted(productId)));

    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }
}

package com.ecomm.productservice.service;

import com.ecomm.productservice.exception.exceptions.ErrorWhileCreatingException;
import com.ecomm.productservice.exception.exceptions.ResourceNotFoundException;
import com.ecomm.productservice.model.Product;
import com.ecomm.productservice.repository.ProductRepository;
import com.ecomm.productservice.utility.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ApiResponse<List<Product>> getAllProducts() {
        return ApiResponse.<List<Product>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(productRepository.findAll())
                .build();
    }

    public ApiResponse<Product> getProductById(Long id) {
        return ApiResponse.<Product>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id)))
                .build();
    }

    public ApiResponse<Product> createProduct(Product product) {
        try {
            return ApiResponse.<Product>builder()
                    .status(HttpStatus.CREATED.value())
                    .message("success")
                    .data(productRepository.save(product))
                    .build();
        } catch (Exception e) {
            throw new ErrorWhileCreatingException("Error while creating product, Please try again");
        }
    }

    public ApiResponse<Product> updateProduct(Long id, Product product) {
        Product existingProduct = getProductById(id).getData();
        try {
            existingProduct.setName(product.getName());
            existingProduct.setBrand(product.getBrand());
            existingProduct.setCategories(product.getCategories());
            return ApiResponse.<Product>builder()
                    .status(HttpStatus.OK.value())
                    .message("success")
                    .data(productRepository.save(existingProduct))
                    .build();
        } catch (Exception e) {
            throw new ErrorWhileCreatingException("Error while updating product, Please try again");
        }
    }
}

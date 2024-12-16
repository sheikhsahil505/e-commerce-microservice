package com.ecomm.productservice.service;

import com.ecomm.productservice.exception.exceptions.ErrorWhileCreatingException;
import com.ecomm.productservice.exception.exceptions.ResourceNotFoundException;
import com.ecomm.productservice.model.ProductAttributes;
import com.ecomm.productservice.model.ProductStatus;
import com.ecomm.productservice.repository.ProductAttributesRepository;
import com.ecomm.productservice.utility.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAttributesService {

    private final ProductAttributesRepository productAttributesRepository;
    private final ProductService productService;

    public ApiResponse<List<ProductAttributes>> getAllProductAttributes() {
        return ApiResponse.<List<ProductAttributes>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(productAttributesRepository.findAll())
                .build();
    }

    public ApiResponse<ProductAttributes> getProductAttributes(Long id) {
        return ApiResponse.<ProductAttributes>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(productAttributesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product attributes not found with id: " + id)))
                .build();
    }

    public ApiResponse<ProductAttributes> createProductAttributes(ProductAttributes productAttributes, Long productId) {
        try {
            productAttributes.setProduct(productService.getProductById(productId).getData());
            return ApiResponse.<ProductAttributes>builder()
                    .status(HttpStatus.CREATED.value())
                    .message("success")
                    .data(productAttributesRepository.save(productAttributes))
                    .build();
        } catch (Exception e) {
            throw new ErrorWhileCreatingException("Error while creating product attributes, Please try again");
        }
    }

    public ApiResponse<ProductAttributes> updateProductAttributes(ProductAttributes productAttributes, Long id) {
        ProductAttributes existingProductAttributes = productAttributesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product attributes with id: " + id + " not found."));

        if (productAttributes.getSkuCode() != null) {
            existingProductAttributes.setSkuCode(productAttributes.getSkuCode());
        }
        if (productAttributes.getName() != null) {
            existingProductAttributes.setName(productAttributes.getName());
        }
        if (productAttributes.getDescription() != null) {
            existingProductAttributes.setDescription(productAttributes.getDescription());
        }
        if (productAttributes.getPrice() != null) {
            existingProductAttributes.setPrice(productAttributes.getPrice());
        }
        if (productAttributes.getStock() != null) {
            existingProductAttributes.setStock(productAttributes.getStock());
        }
        if (productAttributes.getDiscount() != null) {
            existingProductAttributes.setDiscount(productAttributes.getDiscount());
        }
        if (productAttributes.getImageUrls() != null) {
            existingProductAttributes.setImageUrls(productAttributes.getImageUrls());
        }
        if (productAttributes.getOtherSpecificAttributes() != null) {
            existingProductAttributes.setOtherSpecificAttributes(productAttributes.getOtherSpecificAttributes());
        }

        return ApiResponse.<ProductAttributes>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(productAttributesRepository.save(existingProductAttributes))
                .build();
    }

    public ApiResponse<ProductAttributes> activateDeactivateProductAttributes(Long id) {
        ProductAttributes existingProductAttributes = productAttributesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product attributes with id: " + id + " not found."));

        if (existingProductAttributes.getProductStatus() != null && existingProductAttributes.getProductStatus() == ProductStatus.ACTIVE) {
            existingProductAttributes.setProductStatus(ProductStatus.INACTIVE);
        } else if (existingProductAttributes.getProductStatus() != null && existingProductAttributes.getProductStatus() == ProductStatus.INACTIVE) {
            existingProductAttributes.setProductStatus(ProductStatus.ACTIVE);
        }

        return ApiResponse.<ProductAttributes>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(productAttributesRepository.save(existingProductAttributes))
                .build();
    }
}

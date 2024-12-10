package com.ecomm.productservice.controller;

import com.ecomm.productservice.model.ProductAttributes;
import com.ecomm.productservice.service.ProductAttributesService;
import com.ecomm.productservice.utility.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product/details")
@RequiredArgsConstructor
public class ProductAttributesController {

    private final ProductAttributesService productAttributesService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductAttributes>>> getAllProductsAttributes() {
        return ResponseEntity.ok(productAttributesService.getAllProductAttributes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductAttributes>> getProductAttributesById(@PathVariable Long id) {
        return ResponseEntity.ok(productAttributesService.getProductAttributes(id));
    }

    @PostMapping("/create/{productId}")
    public ResponseEntity<ApiResponse<ProductAttributes>> createProductAttributes(@RequestBody @Valid ProductAttributes productAttributes, @PathVariable Long productId) {
        return new ResponseEntity<>(productAttributesService.createProductAttributes(productAttributes, productId), HttpStatus.CREATED);
    }

    @PutMapping("/update/{productAttributeId}")
    public ResponseEntity<ApiResponse<ProductAttributes>> updateProductAttributes(@RequestBody ProductAttributes productAttributes, @PathVariable Long productAttributeId) {
        return ResponseEntity.ok(productAttributesService.updateProductAttributes(productAttributes, productAttributeId));
    }
}

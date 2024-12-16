package com.ecomm.productservice.es.controller;

import com.ecomm.productservice.es.document.CategoryDocument;
import com.ecomm.productservice.es.document.ProductDocument;
import com.ecomm.productservice.es.repository.CategoryElasticsearchRepository;
import com.ecomm.productservice.es.repository.ProductAttributesElasticsearchRepository;
import com.ecomm.productservice.es.repository.ProductElasticsearchRepository;
import com.ecomm.productservice.model.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/product/search")
@RequiredArgsConstructor
public class ProductSearchController {
    private final ProductElasticsearchRepository productEsRepository;
    private final ProductAttributesElasticsearchRepository attributesEsRepository;
    private final CategoryElasticsearchRepository categoryEsRepository;

    @GetMapping("/products")
    public List<ProductDocument> searchProducts(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) ProductStatus status
    ) {
        // Complex search with multiple optional parameters
        if (brand != null && name != null) {
            return productEsRepository.findByBrandAndNameContaining(brand, name);
        }

        if (name != null) {
            return productEsRepository.findByNameContaining(name);
        }

        if (minPrice != null && maxPrice != null) {
            return attributesEsRepository.findByPriceBetween(minPrice, maxPrice)
                    .stream()
                    .map(attr -> productEsRepository.findById(attr.getId()).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        if (status != null) {
            return attributesEsRepository.findByProductStatus(status)
                    .stream()
                    .map(attr -> productEsRepository.findById(attr.getId()).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    @GetMapping("/categories")
    public CategoryDocument searchCategory(@RequestParam String name) {
        return categoryEsRepository.findByName(name);
    }
}
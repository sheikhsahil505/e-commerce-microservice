package com.ecomm.productservice.es.repository;

import com.ecomm.productservice.es.document.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductElasticsearchRepository extends ElasticsearchRepository<ProductDocument, String> {

    // Search by brand
    List<ProductDocument> findByBrand(String brand);

    // Search by name (full-text search)
    List<ProductDocument> findByNameContaining(String name);

    // Complex search across multiple attributes
    List<ProductDocument> findByBrandAndNameContaining(String brand, String name);
}
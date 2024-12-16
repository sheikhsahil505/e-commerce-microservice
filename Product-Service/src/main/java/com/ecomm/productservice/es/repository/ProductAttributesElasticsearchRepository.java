package com.ecomm.productservice.es.repository;

import com.ecomm.productservice.es.document.ProductAttributesDocument;
import com.ecomm.productservice.model.ProductStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductAttributesElasticsearchRepository extends ElasticsearchRepository<ProductAttributesDocument, String> {
    // Search by SKU code
    ProductAttributesDocument findBySkuCode(String skuCode);

    // Search by price range
    List<ProductAttributesDocument> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // Search by product status
    List<ProductAttributesDocument> findByProductStatus(ProductStatus status);
}
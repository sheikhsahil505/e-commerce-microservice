package com.ecomm.productservice.es.repository;

import com.ecomm.productservice.es.document.CategoryDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CategoryElasticsearchRepository extends ElasticsearchRepository<CategoryDocument, String> {
    // Search by category name
    CategoryDocument findByName(String name);

    // Search categories with specific subcategories
    List<CategoryDocument> findBySubCategories(String subCategory);
}
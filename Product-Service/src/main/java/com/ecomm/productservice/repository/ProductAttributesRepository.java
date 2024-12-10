package com.ecomm.productservice.repository;

import com.ecomm.productservice.model.ProductAttributes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributesRepository extends JpaRepository<ProductAttributes, Long> {
}

package com.user.user_service.product;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ProductDTO {
    private Long id;
    private String brand;
    private String name;
    private String createdAt;
    private String updatedAt;
    private List<ProductAttributesDTO> productAttributes;
    private List<CategoryDTO> categories;
    private Long sellerId;

    // Getters and Setters
    @Data
    public static class ProductAttributesDTO {
        private Long id;
        private String skuCode;
        private String name;
        private String description;
        private Float price;
        private Long stock;
        private Float discount;
        private String productStatus;
        private List<String> imageUrls;
        private Map<String, String> otherSpecificAttributes;
        private String createdAt;
        private String updatedAt;
    }

    @Data
    public static class CategoryDTO {
        private Long id;
        private String name;
        private String description;
        private List<String> subCategories;
        private String createdAt;
        private String updatedAt;
    }
}
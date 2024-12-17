package com.user.user_service.product;

import com.devproblems.CategoriesProto;
import com.devproblems.ProductAttributesProto;
import com.devproblems.ProductProto;
import com.devproblems.ProductStatusProto;
import com.user.user_service.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.user.user_service.product.ProductDTO.CategoryDTO;
import com.user.user_service.product.ProductDTO.ProductAttributesDTO;
import com.user.user_service.product.ProductDTO.ProductStatusEnum;

@Component
public class ProductMapperGrpc {

    // Map ProductProto to ProductDTO
    public ProductDTO mapToProductEntity(ProductProto productProto) {
        ProductDTO product = new ProductDTO();
        product.setId(productProto.getId());
        product.setName(productProto.getName());
        product.setBrand(productProto.getBrand());
        product.setCreatedAt(String.valueOf(LocalDateTime.parse(productProto.getCreatedAt())));
        product.setUpdatedAt(String.valueOf(LocalDateTime.parse(productProto.getUpdatedAt())));
        product.setSellerId(productProto.getSellerId());

        List<ProductDTO.ProductAttributesDTO> productAttributesList = productProto.getProductAttributesList().stream()
                .map(this::mapToProductAttributesEntity)
                .toList();

        product.setProductAttributes(productAttributesList);

        List<CategoryDTO> categoriesList = productProto.getCategoriesList().stream()
                .map(this::mapToCategoryEntity)
                .toList();

        product.setCategories(categoriesList);

        return product;
    }

    // Map ProductDTO to ProductProto
    public ProductProto mapToProductProto(ProductDTO product) {
        List<ProductAttributesProto> productAttributesList = (product.getProductAttributes() != null && !product.getProductAttributes().isEmpty())
                ? product.getProductAttributes().stream()
                .map(this::mapToProductAttributesProto)
                .toList()
                : new ArrayList<>();  // If null or empty, initialize an empty list

        List<CategoriesProto> categoriesList = (product.getCategories() != null && !product.getCategories().isEmpty())
                ? product.getCategories().stream()
                .map(this::mapToCategoryProto)
                .toList()
                : new ArrayList<>();  // If null or empty, initialize an empty list

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        // Create ProductProto builder and conditionally add attributes and categories
        ProductProto.Builder productProtoBuilder = ProductProto.newBuilder()
                .setName(product.getName())
                .setBrand(product.getBrand())
                .setCreatedAt(LocalDateTime.now().toString())
                .setUpdatedAt(LocalDateTime.now().toString())
                .setSellerId(currentUser.getId());

        // Only add product attributes if not empty
        if (!productAttributesList.isEmpty()) {
            productProtoBuilder.addAllProductAttributes(productAttributesList);
        }

        // Only add categories if not empty
        if (!categoriesList.isEmpty()) {
            productProtoBuilder.addAllCategories(categoriesList);
        }

        return productProtoBuilder.build();
    }

    // Map ProductAttributesProto to ProductAttributesDTO
    private ProductAttributesDTO mapToProductAttributesEntity(ProductAttributesProto proto) {
        ProductAttributesDTO attributes = new ProductAttributesDTO();
        attributes.setId(proto.getId());
        attributes.setName(proto.getName());
        attributes.setSkuCode(proto.getSkuCode());
        attributes.setDescription(proto.getDescription());
        attributes.setPrice(proto.getPrice());
        attributes.setStock(proto.getStock());
        attributes.setDiscount(proto.getDiscount());
        attributes.setProductStatus(ProductStatusEnum.valueOf(proto.getProductStatus().name()));
        attributes.setImageUrls(new ArrayList<>(proto.getImageUrlsList()));
        attributes.setOtherSpecificAttributes(proto.getOtherSpecificAttributesMap());
        attributes.setCreatedAt(String.valueOf(LocalDateTime.parse(proto.getCreatedAt())));
        attributes.setUpdatedAt(String.valueOf(LocalDateTime.parse(proto.getUpdatedAt())));
        return attributes;
    }

    // Map ProductAttributesDTO to ProductAttributesProto
    private ProductAttributesProto mapToProductAttributesProto(ProductAttributesDTO entity) {
        return ProductAttributesProto.newBuilder()
                .setId(entity.getId())
                .setName(entity.getName())
                .setSkuCode(entity.getSkuCode())
                .setDescription(entity.getDescription())
                .setPrice(entity.getPrice().floatValue())
                .setStock(entity.getStock())
                .setDiscount(entity.getDiscount().floatValue())
                .setProductStatus(ProductStatusProto.valueOf(entity.getProductStatus().name()))
                .addAllImageUrls(entity.getImageUrls())
                .putAllOtherSpecificAttributes(entity.getOtherSpecificAttributes())
                .setCreatedAt(LocalDateTime.now().toString())
                .setUpdatedAt(LocalDateTime.now().toString())
                .build();
    }

    // Map CategoriesProto to CategoryDTO
    private CategoryDTO mapToCategoryEntity(CategoriesProto proto) {
        CategoryDTO category = new CategoryDTO();
        category.setId(proto.getId());
        category.setName(proto.getName());
        category.setDescription(proto.getDescription());
        category.setCreatedAt(String.valueOf(LocalDateTime.parse(proto.getCreatedAt())));
        category.setUpdatedAt(String.valueOf(LocalDateTime.parse(proto.getUpdatedAt())));
        category.setSubCategories(proto.getSubCategoriesList());
        return category;
    }

    // Map CategoryDTO to CategoriesProto
    private CategoriesProto mapToCategoryProto(CategoryDTO entity) {
        return CategoriesProto.newBuilder()
                .setId(entity.getId())
                .build();
    }
}
package com.ecomm.productservice.es.document;

import com.ecomm.productservice.model.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Document(indexName = "product-attributes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAttributesDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String skuCode;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Double)
    private BigDecimal price;

    @Field(type = FieldType.Long)
    private Long stock;

    @Field(type = FieldType.Double)
    private BigDecimal discount;

    @Field(type = FieldType.Text)
    private ProductStatus productStatus;

    @Field(type = FieldType.Text)
    private List<String> imageUrls;

    @Field(type = FieldType.Object)
    private Map<String, String> otherSpecificAttributes;

    @Field(type = FieldType.Date)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date)
    private LocalDateTime updatedAt;
}
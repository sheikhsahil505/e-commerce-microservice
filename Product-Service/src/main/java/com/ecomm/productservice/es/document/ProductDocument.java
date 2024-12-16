package com.ecomm.productservice.es.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Document(indexName = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDocument implements Serializable {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String brand;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Date)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date)
    private LocalDateTime updatedAt;

    @Field(type = FieldType.Nested)
    private List<ProductAttributesDocument> productAttributes;

    @Field(type = FieldType.Nested)
    private List<CategoryDocument> categories;
}

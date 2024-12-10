package com.ecomm.productservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_attributes")
public class ProductAttributes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "SKU code should not be blank.")
    @NotEmpty(message = "SKU code should not be empty.")
    private String skuCode;

    @Column(nullable = false)
    @NotBlank(message = "Name should not be blank.")
    @NotEmpty(message = "Name should not be Empty.")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Description should not be blank.")
    @NotEmpty(message = "Description should not be empty.")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Please define price.")
    private BigDecimal price;

    @Column(nullable = false)
    @NotNull(message = "Please define stock.")
    private Long stock;

    @Column(nullable = false)
    @NotNull(message = "Please define discount.")
    private BigDecimal discount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Please define product status.")
    private ProductStatus productStatus;

    @ElementCollection
    @Column(length = 16300)
    @NotNull(message = "Please upload some product images.")
    private List<String> imageUrls;

    @ElementCollection
    @NotNull(message = "Please add product specific attributes.")
    private Map<String, String> otherSpecificAttributes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference
    private Product product;
}

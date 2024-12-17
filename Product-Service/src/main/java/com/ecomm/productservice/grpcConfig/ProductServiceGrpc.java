package com.ecomm.productservice.grpcConfig;

import com.devproblems.CategoriesProto;
import com.devproblems.ProductAttributesProto;
import com.devproblems.ProductProto;
import com.devproblems.ProductStatusProto;
import com.ecomm.productservice.model.Product;
import com.ecomm.productservice.repository.ProductRepository;
import io.grpc.stub.StreamObserver;
import lombok.NoArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@GrpcService
@NoArgsConstructor
public class ProductServiceGrpc extends com.devproblems.ProductServiceGrpc.ProductServiceImplBase {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceGrpc(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void getProduct(ProductProto request, StreamObserver<ProductProto> responseObserver) {
        Optional<Product> productOptional = productRepository.findById(request.getId());
        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            // Map Product's categories to CategoryProto
            List<CategoriesProto> categoryList = product.getCategories().stream()
                    .map(category -> CategoriesProto.newBuilder()
                            .setId(category.getId())
                            .setName(category.getName())
                            .setDescription(category.getDescription())
                            .setCreatedAt(String.valueOf(category.getCreatedAt()))
                            .setUpdatedAt(String.valueOf(category.getUpdatedAt()))
                            .addAllSubCategories(category.getSubCategories())
                            .build())
                    .toList();

            // Build ProductAttributesProto list
            List<ProductAttributesProto> productAttributesList = product.getProductAttributes().stream()
                    .map(attr -> ProductAttributesProto.newBuilder()
                            .setId(attr.getId())
                            .setName(attr.getName())
                            .setSkuCode(attr.getSkuCode())
                            .setDescription(attr.getDescription())
                            .setPrice(attr.getPrice().floatValue())
                            .setStock(attr.getStock())
                            .setDiscount(attr.getDiscount().floatValue())
                            .setProductStatus(ProductStatusProto.valueOf(attr.getProductStatus().name()))
                            .addAllImageUrls(new ArrayList<>(attr.getImageUrls()))
                            .putAllOtherSpecificAttributes(new HashMap<>(attr.getOtherSpecificAttributes()))
                            .setCreatedAt(attr.getCreatedAt().toString())
                            .setUpdatedAt(attr.getUpdatedAt().toString())
                            .build())
                    .toList();

            // Build ProductProto
            ProductProto productProto = ProductProto.newBuilder()
                    .setId(product.getId())
                    .setName(product.getName())
                    .setBrand(product.getBrand())
                    .setCreatedAt(product.getCreatedAt().toString())
                    .setUpdatedAt(product.getUpdatedAt().toString())
                    .setSellerId(product.getSellerId())
                    .addAllProductAttributes(productAttributesList)
                    .addAllCategories(categoryList) // Add the categories to the response
                    .build();

            responseObserver.onNext(productProto);
        } else {
            responseObserver.onError(new Exception("Product not found"));
        }
        responseObserver.onCompleted();
    }
}
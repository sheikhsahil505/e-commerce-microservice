package com.ecomm.productservice.config;

import com.devproblems.ProductProto;
import com.ecomm.productservice.model.Product;
import com.ecomm.productservice.repository.ProductRepository;
import io.grpc.stub.StreamObserver;
import lombok.NoArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@GrpcService
@NoArgsConstructor
public class ProductServiceGrpc extends com.devproblems.ProductServiceGrpc.ProductServiceImplBase {
    private   ProductRepository productRepository;
    @Autowired
    public ProductServiceGrpc(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void getProduct(ProductProto request, StreamObserver<ProductProto> responseObserver) {
        Optional<Product> productOptional = productRepository.findById(request.getId());
        System.out.println(request.getId());
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductProto productProto = ProductProto.newBuilder()
                    .setId(product.getId())
                    .setName(product.getName())
                    .setBrand(product.getBrand())
                    .build();
            responseObserver.onNext(productProto);
        } else {
            responseObserver.onError(new Exception("Product not found"));
        }
        responseObserver.onCompleted();
    }
}

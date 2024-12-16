//package com.ecomm.productservice.config;
//
//import com.devproblems.ProductServiceGrpc;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GrpcClientConfig {
//
//    @Value("${grpc.product-service.host}")
//    private String productServiceHost;
//
//    @Value("${grpc.product-service.port}")
//    private int productServicePort;
//
//    @Bean
//    public ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub() {
//        ManagedChannel channel = ManagedChannelBuilder
//                .forAddress(productServiceHost, productServicePort)
//                .usePlaintext()
//                .build();
//        return ProductServiceGrpc.newBlockingStub(channel);
//    }
//}

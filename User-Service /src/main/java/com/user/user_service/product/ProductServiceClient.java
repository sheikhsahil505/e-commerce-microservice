package com.user.user_service.product;

import com.devproblems.Product;
import com.devproblems.ProductAttributesProto;
import com.devproblems.ProductProto;
import com.devproblems.ProductServiceGrpc;
import com.google.protobuf.Descriptors;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.common.codec.GrpcCodec;
import org.springframework.stereotype.Service;
import net.devh.boot.grpc.client.inject.GrpcClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceClient {

    public ProductDTO getProducts(int id) {
        ManagedChannel channel = NettyChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
        ProductServiceGrpc.ProductServiceBlockingStub stub = ProductServiceGrpc.newBlockingStub(channel);

        ProductProto productProto = ProductProto.newBuilder().setId(id).build();
        ProductProto product = stub.getProduct(productProto);
        channel.shutdown();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setBrand(product.getBrand());
        productDTO.setName(product.getName());
        productDTO.setCreatedAt(product.getCreatedAt());
        productDTO.setUpdatedAt(product.getUpdatedAt());
        productDTO.setSellerId(product.getSellerId());
        List<ProductDTO.ProductAttributesDTO> productAttributesDTOs = product.getProductAttributesList().stream()
                .map(attr -> {
                    ProductDTO.ProductAttributesDTO dto = new ProductDTO.ProductAttributesDTO();
                    dto.setId(attr.getId());
                    dto.setSkuCode(attr.getSkuCode());
                    dto.setName(attr.getName());
                    dto.setDescription(attr.getDescription());
                    dto.setPrice(attr.getPrice());
                    dto.setStock(attr.getStock());
                    dto.setDiscount(attr.getDiscount());
                    dto.setProductStatus(attr.getProductStatus().name());
                    dto.setImageUrls(attr.getImageUrlsList());
                    dto.setOtherSpecificAttributes(attr.getOtherSpecificAttributesMap());
                    dto.setCreatedAt(attr.getCreatedAt());
                    dto.setUpdatedAt(attr.getUpdatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
        productDTO.setProductAttributes(productAttributesDTOs);

        // Map CategoriesProto to CategoryDTO
        List<ProductDTO.CategoryDTO> categoriesDTOs = product.getCategoriesList().stream()
                .map(category -> {
                    ProductDTO.CategoryDTO dto = new ProductDTO.CategoryDTO();
                    dto.setId(category.getId());
                    dto.setName(category.getName());
                    dto.setDescription(category.getDescription());
                    dto.setSubCategories(category.getSubCategoriesList());
                    dto.setCreatedAt(category.getCreatedAt());
                    dto.setUpdatedAt(category.getUpdatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
        productDTO.setCategories(categoriesDTOs);

        return productDTO;
    }
}

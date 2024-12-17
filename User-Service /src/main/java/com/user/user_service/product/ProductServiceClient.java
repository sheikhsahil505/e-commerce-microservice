package com.user.user_service.product;

import com.devproblems.*;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceClient {

    private final ProductMapperGrpc productMapperGrpc;

    public ProductServiceClient(ProductMapperGrpc productMapperGrpc) {
        this.productMapperGrpc = productMapperGrpc;
    }

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
                    dto.setProductStatus(ProductDTO.ProductStatusEnum.valueOf(attr.getProductStatus().name()));
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

    public ProductDTO saveProduct(ProductDTO productDTO) {
        ManagedChannel channel = NettyChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
        ProductServiceGrpc.ProductServiceBlockingStub stub = ProductServiceGrpc.newBlockingStub(channel);
        SaveProductResponse saveProductResponse = stub.saveProduct(productMapperGrpc.mapToProductProto(productDTO));
        return productMapperGrpc.mapToProductEntity(saveProductResponse.getProduct());
    }

    public List<ProductDTO> getProductsBySellerId(String sellerId) {
        ManagedChannel channel = NettyChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
        ProductServiceGrpc.ProductServiceBlockingStub stub = ProductServiceGrpc.newBlockingStub(channel);
        FindMyProductsResponse myProducts = stub.findMyProducts(FindMyProductsRequest.newBuilder().setSellerId(sellerId).build());
        List<ProductDTO> result = new ArrayList<>();
        for (ProductProto productProto : myProducts.getProductsList()) {
            result.add(productMapperGrpc.mapToProductEntity(productProto));
        }
        return result;
    }
}

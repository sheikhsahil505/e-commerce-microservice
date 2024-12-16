package com.user.user_service.product;

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

import java.util.Map;

@Service
public class ProductServiceClient {

//        @GrpcClient(value = "hello-service")
//        private ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;


    public Map<Descriptors.FieldDescriptor, Object> getProducts(int id) {
        ManagedChannel channel = NettyChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
        ProductServiceGrpc.ProductServiceBlockingStub stub =ProductServiceGrpc.newBlockingStub(channel);
        System.out.println(stub.getChannel()+" ");
        if (stub == null) {
            System.out.println("ProductServiceBlockingStub is null");
        } else {
            System.out.println("ProductServiceBlockingStub is not null");
        }
        ProductProto build = ProductProto.newBuilder().setId(id).build();
        ProductProto product = stub.getProduct(build);
        channel.shutdown();
        return product.getAllFields();
    }
}

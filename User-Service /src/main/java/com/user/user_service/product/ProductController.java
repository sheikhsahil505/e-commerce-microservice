package com.user.user_service.product;

import com.devproblems.ProductServiceGrpc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Descriptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user-service/p/")
public class ProductController {


        private  ProductServiceClient productService;

    @Autowired
    public ProductController(ProductServiceClient productService) {
        this.productService = productService;
    }

        @GetMapping("/products/{id}")
        public ProductDTO getProduct(@PathVariable int id) {
        return productService.getProducts(id);
    }

}

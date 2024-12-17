package com.user.user_service.product;

import com.user.user_service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user-service/seller/")
public class ProductController {

    private final ProductServiceClient productService;
    @Autowired
    public ProductController(ProductServiceClient productService) {
        this.productService = productService;
    }

        @GetMapping("/{id}")
        public ProductDTO getProduct(@PathVariable int id) {
        return productService.getProducts(id);
    }
    @PostMapping("/save")
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return productService.saveProduct(productDTO);
    }
    @GetMapping("/my-products")
    public List<ProductDTO> getMyProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return productService.getProductsBySellerId(currentUser.getId());
    }
}

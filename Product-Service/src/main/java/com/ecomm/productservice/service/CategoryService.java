package com.ecomm.productservice.service;

import com.ecomm.productservice.exception.exceptions.ResourceNotFoundException;
import com.ecomm.productservice.model.Category;
import com.ecomm.productservice.repository.CategoryRepository;
import com.ecomm.productservice.utility.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ApiResponse<List<Category>> getAllCategories() {
        return ApiResponse.<List<Category>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(categoryRepository.findAll())
                .build();
    }

    public ApiResponse<Category> getCategoryById(Long id) {
        return ApiResponse.<Category>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with id: " + id + " not found")))
                .build();
    }

    public ApiResponse<Category> createCategory(Category category) {
        return ApiResponse.<Category>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(categoryRepository.save(category))
                .build();
    }
}

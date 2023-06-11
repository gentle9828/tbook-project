package com.example.itemservice.repository;

import com.example.itemservice.dto.FilterProductRequest;
import com.example.itemservice.dto.ProductFilterDto;
import com.example.itemservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductFilterRepository {
    Page<Product> filterProducts (ProductFilterDto productDto, Pageable pageable);
}

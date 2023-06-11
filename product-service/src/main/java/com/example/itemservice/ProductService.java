package com.example.itemservice;

import com.example.itemservice.vo.ResponseProduct;

import java.util.List;

public interface ProductService {
    List<ResponseProduct> getAllProducts();

    ResponseProduct findById(Long id);
}

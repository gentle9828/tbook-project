package com.example.itemservice;

import com.example.itemservice.entity.Product;
import com.example.itemservice.vo.ResponseProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceIml implements ProductService{
    private final ProductRepository productRepository;
    private final Environment env;

    @Override
    public List<ResponseProduct> getAllProducts() {
        List<Product> products = productRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();

        List<ResponseProduct> findProduct = new ArrayList<>();
        products.forEach(v -> findProduct.add(modelMapper.map(v, ResponseProduct.class)));
        return findProduct;
    }

    @Override
    public ResponseProduct findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 제품이 없습니다"));
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(product, ResponseProduct.class);
    }
}

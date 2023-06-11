package com.example.itemservice;

import com.example.itemservice.dto.FilterProductRequest;
import com.example.itemservice.dto.ProductFilterDto;
import com.example.itemservice.entity.Product;
import com.example.itemservice.repository.ProductFilterRepository;
import com.example.itemservice.vo.ResponseProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/product-service")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductFilterRepository productFilterRepository;

    @GetMapping("/health_check")
    public String status(HttpServletRequest request){
        return String.format("Server on port %s", request.getServerPort());
    }

    @GetMapping("/{id}")
    public ResponseProduct findById(@PathVariable Long id){
        return productService.findById(id);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ResponseProduct>> findAllProducts(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getAllProducts());
    }

    @GetMapping("/products/filter")
    public Page<Product> filterProducts(@ModelAttribute ProductFilterDto productFilterDto, Pageable pageable) {
        return productFilterRepository.filterProducts(productFilterDto, pageable);
    }
}

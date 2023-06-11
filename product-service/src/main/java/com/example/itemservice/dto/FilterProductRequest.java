package com.example.itemservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class FilterProductRequest {
    private List<String> productMadeBy;
    private List<String> productWeightRange;
    private List<String> productPriceRange;
}

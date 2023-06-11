package com.example.itemservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class FilterProductResponse {
    private List<String> productMadeBy;
    private List<String> productWeightRange;
    private List<String> productPriceRange;
}

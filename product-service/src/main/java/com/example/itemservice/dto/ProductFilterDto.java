package com.example.itemservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductFilterDto {
    private List<String> productMadeBy;
    private List<String> productWeightRange;
    private List<String> productPriceRange;
}


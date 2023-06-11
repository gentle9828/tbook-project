package com.example.itemservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ProductCartResponse {
    private String userId;

    private String productId;
    private String productImage;
    private String productName;
    private String productMadeBy;
    private int productPrice;

    @Builder
    public ProductCartResponse(String userId, String productId, String productImage, String productName, String productMadeBy, int productPrice) {
        this.userId = userId;
        this.productId = productId;
        this.productImage = productImage;
        this.productName = productName;
        this.productMadeBy = productMadeBy;
        this.productPrice = productPrice;
    }
}

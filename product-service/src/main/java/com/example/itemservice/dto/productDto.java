package com.example.itemservice.dto;

import lombok.Data;

@Data
public class productDto {
    private String productId;
    private String productName;
    private int stock;
    private int unitPrice;

    private String orderId;
    private String userId;
}

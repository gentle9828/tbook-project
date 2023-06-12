package com.example.itemservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseProduct {
    private String productId;
    private String productMadeBy;
    private String productName;
    private String productCpu;
    private String productImage;
    private int productRamCapacity;
    private String productRamDetail;
    private int productStorageCapacity;
    private String productStorageDetail;
    private String productDisplaySize;
    private String productDisplayDetail;
    private String productGraphic;
    private BigDecimal productBattery;
    private BigDecimal productWeight;
    private int productPrice;   

    private int stock;
    private int unitPrice;
    private LocalDateTime createdAt;
}

package com.example.itemservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter @Setter
@Table(name="products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_made_by", nullable = false)
    private String productMadeBy;

    @Column(name = "product_cpu", nullable = false)
    private String productCpu;

    @Column(name = "product_ram_capacity", nullable = false)
    private int productRamCapacity;

    @Column(name = "product_ram_detail", nullable = false)
    private String productRamDetail;

    @Column(name = "product_storage_capacity", nullable = false)
    private int productStorageCapacity;

    @Column(name = "product_storage_detail", nullable = false)
    private String productStorageDetail;

    @Column(name = "product_display_size", nullable = false)
    private String productDisplaySize;

    @Column(name = "product_display_detail", nullable = false)
    private String productDisplayDetail;

    @Column(name = "product_graphic", nullable = false)
    private String productGraphic;

    @Column(name = "product_battery", nullable = false, precision = 10, scale = 2)
    private BigDecimal productBattery;

    @Column(name = "product_weight", nullable = false, precision = 10, scale = 2)
    private BigDecimal productWeight;

    @Column(name = "product_price", nullable = false)
    private int productPrice;

    @Column(name = "product_stock", nullable = false)
    private int stock;

//    @CreatedDate
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private LocalDateTime createdAt;
}

package com.ll.domain.product.entity;

import com.ll.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product extends BaseEntity {
    String product_name;
    int price;
    String description;
    int order_count;
    String product_image;
    int stock;
}

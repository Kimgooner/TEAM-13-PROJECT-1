package com.ll.domain.product.entity;

import com.ll.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ProductImage extends BaseEntity {
    private int fileId;
    private int productId;
}

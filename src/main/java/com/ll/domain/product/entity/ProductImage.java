package com.ll.domain.product.entity;

import com.ll.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage extends BaseEntity {

    private String imageUrl; // 이미지 URL

    private int imageOrder; // 이미지 순서

    private boolean isDefault; // 대표 이미지 여부

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // 연관된 상품
}

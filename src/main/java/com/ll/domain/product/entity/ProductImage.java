package com.ll.domain.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage extends BaseEntity {

    private String imageUrl; // 이미지 URL

    private int imageOrder; // 이미지 순서

    @Setter
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product; // 연관된 상품
}

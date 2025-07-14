package com.ll.domain.wishList.entity;

import com.ll.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class WishList extends BaseEntity {
    private int product_id;
    private String email;
    private int product_id2;
}

package com.ll.domain.order.dto;

import com.ll.domain.order.entity.WishList;

import java.time.LocalDateTime;

public record WishListDto(
        int id,
        LocalDateTime createTime,
        LocalDateTime modifyTime,
        int productId,
        String productName,
        int memberId,
        String email
) {
    public WishListDto(WishList wishList) {
        this(
                wishList.getId(),
                wishList.getCreateDate(),
                wishList.getModifyDate(),

                // Product 정보 (null 체크)
                wishList.getProduct() != null ? wishList.getProduct().getId() : 0,
                wishList.getProduct() != null ? wishList.getProduct().getProductName() : "알 수 없음",

                // Member 정보 (null 체크)
                wishList.getMember() != null ? wishList.getMember().getId() : 0,
                wishList.getMember() != null ? wishList.getMember().getEmail() : "알 수 없음"
        );
    }
}

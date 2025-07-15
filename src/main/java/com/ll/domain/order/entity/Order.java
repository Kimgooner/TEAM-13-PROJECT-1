package com.ll.domain.order.entity;

import com.ll.domain.member.entity.Member;
import com.ll.domain.product.entity.Product;
import com.ll.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Order extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private int order_count;

    @Column(length = 20)
    private String product_name;

    private int total_price;

    @Column(length = 300)

    private String address;

    private boolean delivery_status;

    @Enumerated(EnumType.STRING) // Enum의 이름을 DB에 문자열로 저장하도록 설정
    private OrderStatus order_status; // 원래 Enum 이름 유지

    public enum OrderStatus {
        ORDERED,    // 주문 완료
        DELIVERED   // 배송 완료
    }

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id", nullable = false) // ERD의 product_id 컬럼을 외래키로 사용
    private Product product; // product_id 필드는 Product 엔티티 객체로 대체

    private String email;

    @OneToMany(mappedBy = "order", fetch = LAZY, cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
}

package com.ll.domain.order.entity;

import com.ll.domain.member.entity.Member;
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

    @Column(length = 300)
    private String address;

    private int total_price; // 총 주문 금액

    @Enumerated(EnumType.STRING) // Enum의 이름을 DB에 문자열로 저장하도록 설정
    private OrderStatus order_status; // 원래 Enum 이름 유지

    public enum OrderStatus {
        ORDERED,    // 주문 완료
        DELIVERED   // 배송 완료
    }

    @OneToMany(mappedBy = "order", fetch = LAZY, cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    
    public void addOrderItem(OrderItem orderItem) {
        orderItem.getProduct().decreaseStock(orderItem.getQuantity());
        orderItems.add(orderItem);
    }

    public void clearOrderItems() {
        // 재고 복원 로직
        for (OrderItem orderItem : orderItems) {
            orderItem.getProduct().increaseStock(orderItem.getQuantity());
        }
        orderItems.clear();
    }

    public int calculateTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotal_price)
                .sum();
    }

    public void updateTotalPrice() {
        this.total_price = calculateTotalPrice();
    }
}

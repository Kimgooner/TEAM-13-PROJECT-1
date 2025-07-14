package com.ll.domain.order.entity;

import com.ll.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Order extends BaseEntity {
    private String user_id;
    private int order_count;
    private String product_name;
    private int total_price;
    private String address;
    private boolean delivery_status;
    private enum order_status {
        ORDERED, DELIVERED
    }
    private int product_id;
    private String email;
}

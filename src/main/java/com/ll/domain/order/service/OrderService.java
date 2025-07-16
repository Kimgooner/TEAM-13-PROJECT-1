package com.ll.domain.order.service;

import com.ll.domain.member.entity.Member;
import com.ll.domain.member.repository.MemberRepository;
import com.ll.domain.order.entity.Order;
import com.ll.domain.order.entity.OrderItem;
import com.ll.domain.order.repository.OrderRepository;
import com.ll.domain.product.entity.Product;
import com.ll.domain.product.repository.ProductRepository;
import com.ll.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(int id) {
        return orderRepository.findById(id);
    }

    public void delete(Order order) {
        orderRepository.delete(order);
    }

    public Order create(int memberId, List<Integer> productIds,  List<Integer> quantities, String address) {
        // 입력값 검증
        if (productIds.size() != quantities.size()) {
            throw new ServiceException("400-4", "상품 ID 목록과 수량 목록의 크기가 일치하지 않습니다.");
        }

        Order order = new Order();
        Member member = memberRepository.findById(memberId).
                orElseThrow(() -> new ServiceException("404-1", "해당 회원이 존재하지 않습니다."));
        order.setMember(member);
        order.setAddress(address);
        order.setOrder_status(Order.OrderStatus.ORDERED);

        for(int i = 0; i < productIds.size(); i++) {
            Product product = productRepository.findById(productIds.get(i))
                    .orElseThrow(() -> new ServiceException("404-2", "해당 상품이 존재하지 않습니다."));
            int quantity = quantities.get(i);

            OrderItem orderItem = new OrderItem(order, product, quantity);
            order.addOrderItem(orderItem);
        }
        order.updateTotalPrice();

        return orderRepository.save(order);
    }

    public Order modify(Order order, List<Integer> productIds, List<Integer> quantities, String address) {
        // 배송 완료된 주문은 수정 불가
        if (order.getOrder_status() == Order.OrderStatus.DELIVERED) {
            throw new ServiceException("400-5", "배송 완료된 주문은 수정할 수 없습니다.");
        }
        // 입력값 검증
        if (productIds.size() != quantities.size()) {
            throw new ServiceException("400-4", "상품 ID 목록과 수량 목록의 크기가 일치하지 않습니다.");
        }

        order.clearOrderItems();

        for(int i=0; i<productIds.size(); i++) {
            Product product = productRepository.findById(productIds.get(i))
                    .orElseThrow(() -> new ServiceException("404-2", "해당 상품이 존재하지 않습니다."));
            int quantity = quantities.get(i);

            OrderItem orderItem = new OrderItem(order, product, quantity);
            order.addOrderItem(orderItem);
        }

        order.setAddress(address);
        order.updateTotalPrice();
        return orderRepository.save(order);
    }
}

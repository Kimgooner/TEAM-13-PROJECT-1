package com.ll.domain.order.service;

import com.ll.domain.order.entity.Order;
import com.ll.domain.order.repository.OrderRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(int id) {
        return orderRepository.findById(id);
    }

    public void delete(Order order) {
        orderRepository.delete(order);
    }

    public Order create( int i,List<Integer> integers,  List<Integer> quantities, String address) {
        Order order = new Order();
        //추가 로직 필요
        return orderRepository.save(order);
    }

    public Order modify(){
        Order order = new Order();
        //추가로직필요
        return orderRepository.save(order);
    }
}

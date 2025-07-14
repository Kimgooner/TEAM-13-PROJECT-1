package com.ll.domain.order.controller;

import com.ll.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiV1OrderController {
    private final OrderService orderService;
}

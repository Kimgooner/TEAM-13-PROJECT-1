package com.ll.domain.order.controller;

import com.ll.domain.order.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiV1WishListController {
    private final WishListService wishListService;
}

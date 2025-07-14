package com.ll.domain.order.service;

import com.ll.domain.order.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListRepository wishListRepository;
}

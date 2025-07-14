package com.ll.domain.order.repository;

import com.ll.domain.order.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Integer> {
}

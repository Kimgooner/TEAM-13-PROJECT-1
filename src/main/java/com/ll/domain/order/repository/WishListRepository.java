package com.ll.domain.order.repository;

import com.ll.domain.member.entity.Member;
import com.ll.domain.order.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Integer> {

    List<WishList> findByMember(Member member);
}

package com.ll.domain.order.service;

import com.ll.domain.member.entity.Member;
import com.ll.domain.member.repository.MemberRepository;
import com.ll.domain.order.entity.WishList;
import com.ll.domain.order.repository.WishListRepository;
import com.ll.domain.product.entity.Product;
import com.ll.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final WishListRepository wishListRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;


    public WishList create(int memberId, int productId){
        Member memeber = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원이다."));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품이다."));

        WishList wishList = new WishList(memeber,product);

        return wishListRepository.save(wishList);
    }

    public List<WishList> getMemberWishList(int memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원이다."));
        return wishListRepository.findByMember(member);
    }

    public Optional<WishList> findById(int id) {
        return wishListRepository.findById(id);
    }

    public void delete(WishList wishList) {
        wishListRepository.delete(wishList);
    }
}

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

    public WishList create(String memberEmail, int productId, int quantity){
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원이다."));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품이다."));
        if(quantity<=0){
            throw new IllegalArgumentException("수량은 1 이상이어야 한다.");
        }

        // 이미 찜 목록에 있는지 확인
        Optional<WishList> existWishList = wishListRepository.findByMemberAndProduct(member, product);

        WishList wishList;
        if (existWishList.isPresent()) {
            // 이미 장바구니에 있으면 수량 업데이트
            wishList = existWishList.get();
            wishList.setQuantity(wishList.getQuantity() + quantity);
            //TODO : 재고확인?
        } else {
            // 없으면 새로 생성
            wishList = new WishList(member, product, quantity);
        }

        return wishListRepository.save(wishList);
    }
    //여긴끝

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

    public void setProductQuantityInWishList(int wishListId, int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("수량은 0 이상이어야 한다.");
        }

        WishList wishList = wishListRepository.findById(wishListId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 찜 목록 항목이다."));

        if (newQuantity == 0) {
            wishListRepository.delete(wishList);
        } else {
            wishList.setQuantity(newQuantity);
            wishListRepository.save(wishList);
        }
    }

    public void removeWishListItem(int wishListId) {
        WishList wishList = wishListRepository.findById(wishListId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 찜 목록 항목이다."));
        wishListRepository.delete(wishList);
    }

    public void clearWishList(String memberEmail) {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원이다."));
        List<WishList> memberWishLists = wishListRepository.findByMember(member);
        wishListRepository.deleteAll(memberWishLists);
    }
}

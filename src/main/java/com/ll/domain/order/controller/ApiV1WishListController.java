package com.ll.domain.order.controller;

import com.ll.domain.order.dto.WishListDto;
import com.ll.domain.order.entity.WishList;
import com.ll.domain.order.service.WishListService;
import com.ll.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wishlist")
@Tag(name = "ApiV1WishListController", description = "API wishList 목록 컨트롤러")
public class ApiV1WishListController {
    private final WishListService wishListService;

    @GetMapping("/member/{memberId}")
    @Transactional(readOnly = true)
    @Operation(summary = "특정 회원의 wishList 목록 조회")
    public List<WishListDto> getWishList(@PathVariable("memberId") int memberId) {
        List<WishList> wishLists = wishListService.getMemberWishList(memberId);

        return wishLists
                .stream()
                .map(WishListDto::new)
                .toList();
    }

    record AddWishListRequest(
            @NotNull(message = "회원 ID는 필수이다.")
            int memberId,
            @NotNull(message = "상품 ID는 필수이다.")
            int productId
    ) {
    }

    @PostMapping
    @Transactional
    @Operation(summary = "wishList 목록에 상품 추가")
    public RsData<WishListDto> create(@Valid @RequestBody
                                      AddWishListRequest req) {
        WishList wishList = wishListService.create(
                req.memberId(),
                req.productId
        );

        return new RsData<>(
                "200-1",
                "%d번 상품이 위시리스트에 담겼습니다.".formatted(wishList.getId()),
                new WishListDto(wishList)
        );
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "wishList 상품 제거")
    public RsData<Void> delete(@PathVariable("id") int id) {
        WishList wishList = wishListService.findById(id).get();
        wishListService.delete(wishList);
        return new RsData<>(
                "200-1",
                "%d번 위시리스트가 삭제되었습니다.".formatted(id)
        );
    }
}

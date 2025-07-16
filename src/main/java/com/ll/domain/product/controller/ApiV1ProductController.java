package com.ll.domain.product.controller;

import com.ll.domain.product.dto.CreateProductRequestDto;
import com.ll.domain.product.dto.MenuProductDto;
import com.ll.domain.product.entity.Product;
import com.ll.domain.product.service.ProductService;
import com.ll.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ApiV1ProductController {

    private final ProductService productService;

    @Transactional
    @PostMapping(value = "/create", consumes = "multipart/form-data")
    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다.\n" +
            "'contentType: multipart/form-data' 형식으로 요청해야 합니다.")
    public RsData<Product> createProduct(
            @ModelAttribute CreateProductRequestDto productDto,
            @RequestParam("mainImage") MultipartFile mainImage

    ) {
        Product product = productService.createProduct(productDto, mainImage);
        return new RsData<>("200-1", "상품이 등록되었습니다.", product);
    }

    @Transactional(readOnly = true)
    @GetMapping("/category/{category}")
    @Operation(summary = "카테고리별 메뉴 상품 조회", description = "특정 카테고리에 속하는 메뉴 상품 목록을 조회합니다.\n" +
    "'category'는 대문자로 입력해야 합니다. 현재 카테고리는 'ALL', 'COFFEE', 'TEA', 'JUICE 'DESSERT'가 있습니다." +
    " url 예시: /api/v1/products/category/ALL , /api/v1/products/category/COFFEE 등")
    public RsData<List<MenuProductDto>> getMenuProductsByCategory(@PathVariable String category) {
        List<MenuProductDto> menuProducts = productService.getMenuProductsByCategory(category);
        return new RsData<>("200-1", "%s 메뉴 상품 목록을 조회했습니다.".formatted(category), menuProducts);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    @Operation(summary = "단건 상품 상세 조회", description = "특정 상품 상세 데이터를 조회합니다.\n" +
            " url 예시: /api/v1/products/1 , /api/v1/products/2 등")
    public RsData<Product> getProduct(@PathVariable int id) {
        Product product = productService.getProduct(id);
        return new RsData<>("200-1", "%d번 상품 목록을 조회했습니다.".formatted(id), product);
    }

    @Transactional(readOnly = true)
    @GetMapping
    @Operation(summary = "다건 상품 상세 조회", description = "모든 상품 상세 데이터를 조회합니다.")
    public RsData<List<Product>> getProducts() {
        List<Product> products = productService.getProducts();
        return new RsData<>("200-1", "상세 상품 목록을 조회했습니다.", products);
    }
}


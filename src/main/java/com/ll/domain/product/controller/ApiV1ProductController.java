package com.ll.domain.product.controller;

import com.ll.domain.product.dto.CreateProductRequestDto;
import com.ll.domain.product.entity.Product;
import com.ll.domain.product.service.ProductService;
import com.ll.global.rsData.RsData;
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
    public RsData<Product> createProduct(
            @ModelAttribute CreateProductRequestDto productDto,
            @RequestParam("mainImage") MultipartFile mainImage,
            @RequestParam(value = "images", required = false) List<MultipartFile> images
    ) {
        Product product = productService.createProduct(productDto, mainImage, images);
        return new RsData<>("200-1", "상품이 등록되었습니다.", product);
    }
}


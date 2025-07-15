package com.ll.domain.product.service;

import com.ll.domain.product.dto.CreateProductRequestDto;
import com.ll.domain.product.entity.Product;
import com.ll.domain.product.entity.ProductCategory;
import com.ll.domain.product.entity.ProductImage;
import com.ll.domain.product.entity.ProductStatus;
import com.ll.domain.product.repository.ProductImageRepository;
import com.ll.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;


    @Value("${product.upload-dir}")
    private String dirName;

    public Product createProduct(CreateProductRequestDto productDto, MultipartFile mainImage, List<MultipartFile> images) {

        String mainImageUrl = saveFile(mainImage);

        Product product = Product.builder()
                .productName(productDto.getProductName())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .productImage(mainImageUrl)
                .stock(productDto.getStock())
                .status(ProductStatus.valueOf(productDto.getStatus()))
                .category(ProductCategory.valueOf(productDto.getCategory()))
                .build();

        // 추가 이미지 저장 (images가 null이 아닐 때만)
        int order = 0;
        if (images != null) {
            for (MultipartFile file : images) {
                String imageUrl = saveFile(file);
                ProductImage image = ProductImage.builder()
                        .imageUrl(imageUrl)
                        .imageOrder(order)
                        .product(product)
                        .build();
                product.addImage(image);
                order++;
            }
        }

        productRepository.save(product);
        return product;
    }

    private String saveFile(MultipartFile file) {
        try {
            String uploadDir = System.getProperty("user.dir") + "/" + dirName;
            System.out.println(uploadDir);
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath.toFile());
            return "/productImages/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }
}


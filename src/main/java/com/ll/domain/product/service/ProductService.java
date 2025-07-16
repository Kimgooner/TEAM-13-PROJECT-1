package com.ll.domain.product.service;

import com.ll.domain.product.dto.CreateProductRequestDto;
import com.ll.domain.product.dto.MenuProductDto;
import com.ll.domain.product.entity.Product;
import com.ll.domain.product.entity.ProductCategory;
import com.ll.domain.product.entity.ProductStatus;
import com.ll.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    @Value("${product.upload-dir}")
    private String dirName;

    public Product createProduct(CreateProductRequestDto productDto, MultipartFile mainImage) {

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

        productRepository.save(product);
        return product;
    }

    private String saveFile(MultipartFile file) {
        try {
            String uploadDir = System.getProperty("user.dir") + "/" + dirName;

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

    public List<MenuProductDto> getMenuProductsByCategory(String category) {
        List<Product> products;
        if (category.equals("ALL")) {
            products = productRepository.findAll();
        }else {
            products = productRepository.findByCategory(ProductCategory.valueOf(category));
        }

        return products.stream()
                //.sorted(Comparator.comparing(Product::getId)) // ID 오름차순 정렬
                .map(product -> {
                    MenuProductDto dto = new MenuProductDto();
                    dto.setId(product.getId());
                    dto.setProductName(product.getProductName());
                    dto.setPrice(product.getPrice());
                    dto.setCategory(product.getCategory().name());
                    dto.setProductImage("http://localhost:8080" + product.getProductImage());
                    return dto;
                })
                .toList();
    }
}


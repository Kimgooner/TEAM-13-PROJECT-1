package com.ll.domain.product.controller;

import com.ll.domain.product.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class ApiV1ProductControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductService productService;

    @Value("${product.upload-dir}")
    private String dirName;

    @Test
    @DisplayName("상품 등록 - form-data 방식")
    void 상품_등록() throws Exception {

        // 대표 이미지
        MockMultipartFile mainImage = new MockMultipartFile("mainImage", "mainImage.jpg", "image/jpeg", "main-image-data".getBytes(StandardCharsets.UTF_8));
        // 이미지 파일 2개 생성
        MockMultipartFile image1 = new MockMultipartFile("images", "image1.jpg", "image/jpeg", "image-data-1".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile image2 = new MockMultipartFile("images", "image2.jpg", "image/jpeg", "image-data-2".getBytes(StandardCharsets.UTF_8));

        // 요청 및 검증
        ResultActions resultActions = mvc.perform(
                multipart("/api/v1/products/create")
                        .file(mainImage)
                        .file(image1)
                        .file(image2)
                        .param("productName", "커피 01")
                        .param("price", "4500")
                        .param("description", "커피 01 설명")
                        .param("stock", "500")
                        .param("status", "판매중")
                        .param("category", "커피")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        ).andDo(print());

        resultActions
                .andExpect(status().isOk());
        // 필요에 따라 응답값 추가 검증

        String uploadDir = System.getProperty("user.dir") + "/" + dirName;
        Path uploadPath = Paths.get(uploadDir); // 테스트용 경로와 일치시킬 것
        if (Files.exists(uploadPath)) {
            // 폴더와 하위 파일 전체 삭제
            Files.walk(uploadPath)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }
}

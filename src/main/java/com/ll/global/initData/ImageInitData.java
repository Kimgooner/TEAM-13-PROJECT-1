package com.ll.global.initData;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@RequiredArgsConstructor
public class ImageInitData {

    @Autowired
    @Lazy
    private ImageInitData self;

    @Value("${product.upload-dir}")
    private String dirName;

    @Bean
    ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
        };
    }

    public void work1() {
        try {
            String uploadDir = System.getProperty("user.dir") + "/" + dirName;

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        }catch (Exception e) {
            throw new RuntimeException("이미지 파일 디렉토리 생성 실패", e);
        }
    }

}

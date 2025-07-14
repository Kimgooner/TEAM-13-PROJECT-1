package com.ll.domain.menu.controller;

import com.ll.domain.menu.dto.MenuDto;
import com.ll.domain.menu.entity.Menu;
import com.ll.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class ApiV1MenuCreateController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<Menu> create(@RequestBody MenuDto dto) {
        Menu savedMenu = menuService.create(dto);
        return ResponseEntity.ok(savedMenu);
    }
}
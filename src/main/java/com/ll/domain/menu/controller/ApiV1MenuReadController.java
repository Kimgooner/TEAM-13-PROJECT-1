package com.ll.domain.menu.controller;


import com.ll.domain.menu.dto.MenuDto;
import com.ll.domain.menu.entity.Menu;
import com.ll.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class ApiV1MenuReadController {
    private final MenuService menuService;

    @GetMapping
    @Transactional(readOnly = true)
    public List<MenuDto> getItems(){
        List<Menu> items = menuService.findAll();

        return items
                .stream()
                .map(MenuDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public MenuDto getItem(@PathVariable Long id) {
        Menu menu = menuService.findById(id).get();

        return new MenuDto(menu);
    }
}

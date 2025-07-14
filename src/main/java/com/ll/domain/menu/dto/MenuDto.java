package com.ll.domain.menu.dto;

import com.ll.domain.menu.entity.Menu;

public record MenuDto (
        Long id,
        String name,
        String image,
        int price

){
    public MenuDto (Menu menu){
        this(
                menu.getId(), menu.getName(), menu.getImage(), menu.getPrice()
        );
    }
}

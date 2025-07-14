package com.ll.domain.menu.service;

import com.ll.domain.menu.dto.MenuDto;
import com.ll.domain.menu.entity.Menu;
import com.ll.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public Menu create(MenuDto dto) {
        Menu menu = new Menu(
                dto.getName(),
                dto.getImage(),
                dto.getPrice()
        );

        return menuRepository.save(menu);
    }
}
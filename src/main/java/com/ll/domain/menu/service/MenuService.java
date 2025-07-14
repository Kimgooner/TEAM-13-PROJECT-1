package com.ll.domain.menu.service;

import com.ll.domain.menu.entity.Menu;
import com.ll.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public long count(){
        return menuRepository.count();
    }

    public Optional<Menu> findById(Long id){ return menuRepository.findById(id);}
    public List<Menu> findAll(){ return menuRepository.findAll();}

    public void modify(Menu menu, String name, String image, int price){ menuRepository.save(menu);}
    public Menu addMenu(Menu menu){ return menuRepository.save(menu);}
    public void deleteById(Long id){ menuRepository.deleteById(id);}

    public void flush(){ menuRepository.flush();}
}

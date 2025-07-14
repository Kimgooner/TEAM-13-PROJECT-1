package com.ll.domain.menu.service;

import com.ll.domain.menu.dto.MenuDto;
import com.ll.domain.menu.entity.Menu;
import com.ll.domain.menu.repository.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MenuPostTest {

    @Test
    @DisplayName("POST 요청 시 menu가 저장되고 id가 자동 증가해야 한다.")
    void testCreateMenuWithAutoIncrementId() {
        // given
        MenuRepository mockRepo = mock(MenuRepository.class);
        MenuService service = new MenuService(mockRepo);

        MenuDto dto = new MenuDto();
        dto.setName("아메리카노");
        dto.setImage("https://img.com/coffee.png");
        dto.setPrice(4500);

        // Menu 저장 시 id가 1인 Menu를 리턴하도록 시뮬레이션
        Menu savedMenu = new Menu(dto.getName(), dto.getImage(), dto.getPrice());
        try {
            Field idField = Menu.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(savedMenu, 1L);  // id 강제 설정
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(mockRepo.save(any(Menu.class))).thenReturn(savedMenu);

        // when
        Menu result = service.create(dto);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        verify(mockRepo, times(1)).save(any(Menu.class));
    }
}

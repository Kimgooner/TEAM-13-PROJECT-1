package com.ll.domain.order.controller;

import com.ll.domain.order.entity.Order;
import com.ll.domain.order.service.OrderService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test") // 테스트 프로파일 활성화 (application-test.yml 사용)
class ApiV1OrderControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private OrderService orderService;

    @Test
    @DisplayName("주문 단건 조회")
    void t4() throws Exception {
        // GIVEN
        // 테스트를 위해 실행 전에 데이터가 있어야 한다. @SpringBootTest는 실제 DB를 사용할 수 있으므로,
        // 테스트용 데이터를 미리 넣어두는 것이 좋다. (예: data.sql 또는 @BeforeEach)
        // 여기서는 ID가 1인 주문이 이미 존재한다고 가정한다.
        int id = 1;

        // WHEN
        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/orders/" + id)
                )
                .andDo(print());

        // THEN
        // 테스트가 독립적으로 실행되도록 서비스 계층을 직접 호출하기보다는, 응답 본문(JSON)의 값을 검증한다.
        Order order = orderService.findById(id).orElse(null);

        resultActions
                .andExpect(handler().handlerType(ApiV1OrderController.class))
                .andExpect(handler().methodName("getItem"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.createDate").value(Matchers.startsWith(order.getCreateDate().toString().substring(0, 19))))
                .andExpect(jsonPath("$.modifyDate").value(Matchers.startsWith(order.getModifyDate().toString().substring(0, 19))))
                // ERROR: 아래 필드들은 Order 엔티티에 존재하지 않는다. 주석 처리.
                // .andExpect(jsonPath("$.orderCount").value(order.getOrder_count()))
                // .andExpect(jsonPath("$.productName").value(order.getProduct_name()))
                .andExpect(jsonPath("$.totalPrice").value(order.getTotal_price()))
                .andExpect(jsonPath("$.address").value(order.getAddress()))
                // OrderDto가 order_status를 올바르게 반환하는지 확인
                .andExpect(jsonPath("$.orderStatus").value(order.getOrder_status().name()))
                // ERROR: email은 Member 객체를 통해 접근해야 한다.
                .andExpect(jsonPath("$.email").value(order.getMember().getEmail()));

    }

    @Test
    @DisplayName("존재하지 않는 주문 단건조회, 404")
    void t6() throws Exception {
        // GIVEN
        int id = Integer.MAX_VALUE;

        // WHEN
        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/orders/" + id)
                )
                .andDo(print());

        // THEN
        // 컨트롤러에서 .get()을 호출하면 NoSuchElementException이 발생하고,
        // GlobalExceptionHandler가 이를 처리하여 적절한 HTTP 상태 코드를 반환해야 한다.
        // 예를 들어 404 Not Found를 반환하도록 설정했다고 가정한다.
        resultActions
                .andExpect(status().isNotFound()); // 혹은 isBadRequest() 등 예외 처리 정책에 따름
    }

    @Test
    @DisplayName("모든 주문 조회 성공")
    void t5() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/orders")
                )
                .andDo(print());

        // THEN
        List<Order> ordersInDb = orderService.findAll();

        // 컨트롤러의 getItems()는 List<OrderDto>를 직접 반환하므로, RsData 형식이 아니다.
        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ApiV1OrderController.class))
                .andExpect(handler().methodName("getItems"))
                .andExpect(jsonPath("$.length()").value(ordersInDb.size()));

        for (int i = 0; i < ordersInDb.size(); i++) {
            Order order = ordersInDb.get(i);
            String basePath = "$[%d].".formatted(i);

            resultActions
                    .andExpect(jsonPath(basePath + "id").value(order.getId()))
                    .andExpect(jsonPath(basePath + "createDate").value(Matchers.startsWith(order.getCreateDate().toString().substring(0, 19))))
                    .andExpect(jsonPath(basePath + "modifyDate").value(Matchers.startsWith(order.getModifyDate().toString().substring(0, 19))))
                    // ERROR: 아래 필드들은 Order 엔티티에 존재하지 않는다.
                    // .andExpect(jsonPath(basePath + "orderCount").value(order.getOrder_count()))
                    // .andExpect(jsonPath(basePath + "productName").value(order.getProduct_name()))
                    .andExpect(jsonPath(basePath + "totalPrice").value(order.getTotal_price()))
                    .andExpect(jsonPath(basePath + "address").value(order.getAddress()))
                    .andExpect(jsonPath(basePath + "orderStatus").value(order.getOrder_status().name()))
                    // ERROR: email은 Member 객체를 통해 접근해야 한다.
                    .andExpect(jsonPath(basePath + "email").value(order.getMember().getEmail()));
        }
    }
}
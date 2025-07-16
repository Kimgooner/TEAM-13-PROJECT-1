package com.ll.domain.order.controller;

import com.ll.domain.order.entity.Order;
import com.ll.domain.order.service.OrderService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ApiV1OrderControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private OrderService orderService;

    @Test
    @DisplayName("주문 단건 조회")
    void t4() throws Exception {
        int id = 1;

        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/orders/" + id)
                )
                .andDo(print());

        Order order = orderService.findById(id).get();

        resultActions
                .andExpect(handler().handlerType(ApiV1OrderController.class))
                .andExpect(handler().methodName("getItem"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.createDate").value(Matchers.startsWith(order.getCreateDate().toString().substring(0, 20))))
                .andExpect(jsonPath("$.modifyDate").value(Matchers.startsWith(order.getModifyDate().toString().substring(0, 20))))
                .andExpect(jsonPath("$.orderCount").value(order.getOrder_count()))
                .andExpect(jsonPath("$.productName").value(order.getProduct_name()))

                .andExpect(jsonPath("$.totalPrice").value(order.getTotal_price()))
                .andExpect(jsonPath("$.address").value(order.getAddress()))
//                .andExpect(jsonPath("$.deliveryStatus").value(order.isDelivery_status()))
                .andExpect(jsonPath("$.email").value(order.getEmail()));

    }

    @Test
    @DisplayName("글 단건조회, 404")
    void t6() throws Exception {
        int id = Integer.MAX_VALUE;

        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/orders/" + id)
                )
                .andDo(print());

        resultActions
                .andExpect(handler().handlerType(ApiV1OrderController.class))
                .andExpect(handler().methodName("getItem"))
                .andExpect(status().isBadRequest()) // GlobalExceptionHandler에서 IllegalArgumentException을 400으로 처리
                .andExpect(jsonPath("$.resultCode").value("400-1"))
                .andExpect(jsonPath("$.msg").value("존재하지 않는 주문이다."));
    }

    @Test
    @DisplayName("t5_모든 주문 조회 성공")
    void t5() throws Exception {
        // Given: 테스트용 주문 미리 생성
//        orderService.createOrder(testMember1.getId(), Arrays.asList(testProduct1.getId()), Arrays.asList(1), "주문1 주소");
//        orderService.createOrder(testMember2.getId(), Arrays.asList(testProduct2.getId(), testProduct3.getId()), Arrays.asList(2, 1), "주문2 주소");
//
//        // When
        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/orders")
                )
                .andDo(print());

        // Then
        List<Order> ordersInDb = orderService.findAll();
        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ApiV1OrderController.class))
                .andExpect(handler().methodName("getItems"))
                .andExpect(jsonPath("$.resultCode").value("200-1"))
                .andExpect(jsonPath("$.data.length()").value(ordersInDb.size()));

        for (int i = 0; i < ordersInDb.size(); i++) {
            Order order = ordersInDb.get(i);
            resultActions
                    .andExpect(jsonPath("$.data[%d].id".formatted(i)).value(order.getId()))
                    .andExpect(jsonPath("$.data[%d].createDate".formatted(i)).value(Matchers.startsWith(order.getCreateDate().toString().substring(0, 20))))
                    .andExpect(jsonPath("$.data[%d].modifyDate".formatted(i)).value(Matchers.startsWith(order.getModifyDate().toString().substring(0, 20))))
                    .andExpect(jsonPath("$.data[%d].orderCount".formatted(i)).value(order.getOrder_count()))
                    .andExpect(jsonPath("$.data[%d].productName".formatted(i)).value(order.getProduct_name()))
                    .andExpect(jsonPath("$.data[%d].totalPrice".formatted(i)).value(order.getTotal_price()))
                    .andExpect(jsonPath("$.data[%d].address".formatted(i)).value(order.getAddress()))
//                    .andExpect(jsonPath("$.data[%d].deliveryStatus".formatted(i)).value(order.isDeliveryStatus()))
                    .andExpect(jsonPath("$.data[%d].orderStatus".formatted(i)).value(order.getOrder_status().name()))
                    .andExpect(jsonPath("$.data[%d].email".formatted(i)).value(order.getEmail()));
        }
    }


}
package org.iclass.mvc.dao;

import lombok.extern.slf4j.Slf4j;
import org.iclass.mvc.dto.OrderDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class OrderMapperTest {

    @Autowired
    private OrderMapper mapper;

    @Test
    @DisplayName("주문한 회원의 이메일을 조회합니다.(중복 제거)")
    void selectByEmail() {
        List<String> emails = mapper.selectOrderByEmail();
        log.info("주문 회원 이메일 : {}",emails);
        assertNotNull(emails);
    }
    @Test
    @DisplayName("주문을 삽입합니다.")
    void insert() {

        OrderDto dto =OrderDto.builder()
                .email("momoo@gmail.com")
                .pcode("AHH6_099")
                .quantity(3)
                .orderdate(LocalDate.of(2023,10,11)).build();
        mapper.insert(dto);
        int idx = dto.getIdx();     //selectKey로 생성된 시퀀스값 가져오기
        OrderDto result = mapper.selectByIdx(idx);
        log.info(" inserted OrderDto : {}",result);
        assertNotNull(result);
    }


    @Test
    @DisplayName("주문을 추가하고 해당 인덱스로 조회합니다.")
    void insertAndSelectOrderByIdx() {
        // 주문을 추가합니다.
        OrderDto dto = OrderDto.builder()
                .email("sanghee@gmail.com")
                .pcode("SSSSS")
                .quantity(5)
                .orderdate(LocalDate.of(2023, 11, 1))
                .build();
        mapper.insert(dto);


        assertNotNull(dto.getIdx());



        OrderDto selectedOrder = mapper.selectByIdx(dto.getIdx());


        assertNotNull(selectedOrder);


        assertEquals("sanghee@gmail.com", selectedOrder.getEmail());
        assertEquals("SSSSS", selectedOrder.getPcode());
        assertEquals(5, selectedOrder.getQuantity());
        assertEquals(LocalDate.of(2023, 11, 1), selectedOrder.getOrderdate());



    }





}
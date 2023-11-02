package org.iclass.mvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.iclass.mvc.dto.OrderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class OrderApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;  //자바객체와 json 사이의 직렬화(writeValueAsString 메소드),역직렬화(readValue 메소드)를 위한 객체

    @Test
    void selectOneOrder() throws Exception {
        mockMvc.perform(get("/api/order/{idx}",1001))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void saveSuccess() throws Exception {
        OrderDto dto =OrderDto.builder()
                .email("momoo@gmail.com")
                .pcode("AHH6_099")
                .quantity(3)
                .orderdate(LocalDate.of(2023,11,1)).build();

        String jsonContent = objectMapper.writeValueAsString(dto);      //직렬화
        mockMvc.perform(post("/api/order")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void saveFail() throws Exception {
        OrderDto dto =OrderDto.builder()
                .email("momoo@gmail.com")
                .pcode("AHH6_099")
                .quantity(3)
                .orderdate(LocalDate.of(2023,10,11)).build();
        String jsonContent = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/api/order")
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());

    }
}
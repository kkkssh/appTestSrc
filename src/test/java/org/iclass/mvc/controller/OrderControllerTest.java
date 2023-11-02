package org.iclass.mvc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc   //MocMvc 를 위한 설정
class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void form() throws Exception {

        mvc.perform(get("/order/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/new"))
                .andDo(print());
    }

    @Test
    void save() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("email", "abc@abcxyz.com");
        map.add("pcode", "AAA_02X");
        map.add("quantity", "3");
        map.add("orderdate", "2023-11-12");

        mvc.perform(MockMvcRequestBuilders.post("/order/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(map))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/order/confirm?idx=1032"))
                .andDo(print());                //여기서 1009 대신에 현재 시퀀스값 +1 로 하세요. (결론, 다음에 들어갈 시퀀스 예상값)
    }
}
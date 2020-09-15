package com.thoughtworks.rslist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class RsListApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getOneRsItemFromList() throws Exception {
        mockMvc.perform(get("/rs/0"))
                .andExpect(content().string("第一条事件,1"));
    }

    @Test
    void getRsItemsFromListByStartNumber() throws Exception {
        mockMvc.perform(get("/rs/list?start=0&end=2"))
                .andExpect(content().string("[第一条事件,1, 第二条事件,2]"));
        mockMvc.perform(get("/rs/list?start=0&end=3"))
                .andExpect(content().string("[第一条事件,1, 第二条事件,2, 第三条事件,3]"));

    }
}

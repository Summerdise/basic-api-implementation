package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.RsItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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

    @Test
    void insertItemIntoList() throws Exception {
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[第一条事件,1, 第二条事件,2, 第三条事件,3]"));
        RsItem rsItem = new RsItem("第四条事件", "4");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(rsItem);
        mockMvc.perform(post("/rs/all").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[第一条事件,1, 第二条事件,2, 第三条事件,3, 第四条事件,4]"));
    }
}

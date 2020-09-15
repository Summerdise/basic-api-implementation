package com.thoughtworks.rslist;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Test
    void fixItemInList() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        RsItem noNameRsItem = new RsItem(null, "修改keyword-1");
        String noNameJson = objectMapper.writeValueAsString(noNameRsItem);
        mockMvc.perform(post("/rs/fix0").content(noNameJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[第一条事件,修改keyword-1, 第二条事件,2, 第三条事件,3]"));

        RsItem noKeywordRsItem = new RsItem("修改name-2", null);
        String noKeywordJson = objectMapper.writeValueAsString(noKeywordRsItem);
        mockMvc.perform(post("/rs/fix0").content(noKeywordJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[修改name-2,修改keyword-1, 第二条事件,2, 第三条事件,3]"));

        RsItem fixRsItem = new RsItem("修改name-3", "修改keyword-3");
        String fixJson = objectMapper.writeValueAsString(fixRsItem);
        mockMvc.perform(post("/rs/fix1").content(fixJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[修改name-2,修改keyword-1, 修改name-3,修改keyword-3, 第三条事件,3]"));
    }
}

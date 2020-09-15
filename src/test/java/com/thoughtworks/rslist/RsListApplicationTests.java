package com.thoughtworks.rslist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class RsListApplicationTests {
    @Autowired
    MockMvc mockMvc;
    @Test
    void contextLoads() throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/rs/list")).andReturn();
        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
        int status = mockHttpServletResponse.getStatus();
        assertEquals(200,status);
        assertEquals("[第一条事件, 第二条事件, 第三条事件]",mockHttpServletResponse.getContentAsString());

    }

}

package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.RsItem;
import com.thoughtworks.rslist.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void getOneRsItemFromList() throws Exception {
        mockMvc.perform(get("/rs/0"))
                .andExpect(content().string("第一条事件,1,xiaowang"));
    }

    @Test
    void getRsItemsFromListByStartNumber() throws Exception {
        mockMvc.perform(get("/rs/list?start=0&end=2"))
                .andExpect(content().string("[第一条事件,1,xiaowang, 第二条事件,2,xiaowang]"));
        mockMvc.perform(get("/rs/list?start=0&end=3"))
                .andExpect(content().string("[第一条事件,1,xiaowang, 第二条事件,2,xiaowang, 第三条事件,3,xiaowang]"));
    }

    @Test
    void insertItemIntoListWithSameUserName() throws Exception {
        UserDto userDto = new UserDto("xiaowang",19,"female","a@thoughtworks.com","18888888888");
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[第一条事件,1,xiaowang, 第二条事件,2,xiaowang, 第三条事件,3,xiaowang]"));
        RsItem rsItem = new RsItem("第四条事件", "4",userDto);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(rsItem);
        mockMvc.perform(post("/rs/all").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[第一条事件,1,xiaowang, 第二条事件,2,xiaowang, 第三条事件,3,xiaowang, 第四条事件,4,xiaowang]"));
    }

    @Test
    void insertItemIntoListWithDifferentUserName() throws Exception {
        UserDto userDto = new UserDto("dawang",19,"female","a@thoughtworks.com","18888888888");
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[第一条事件,1,xiaowang, 第二条事件,2,xiaowang, 第三条事件,3,xiaowang]"));
        RsItem rsItem = new RsItem("第四条事件", "4",userDto);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(rsItem);
        mockMvc.perform(post("/rs/all").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/user/all"))
                .andExpect(content().string("[]"));
        String jsonUser = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/user/register")
                .content(jsonUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/user/all"))
                .andExpect(content().string("[userName=dawang]"));
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[第一条事件,1,xiaowang, 第二条事件,2,xiaowang, 第三条事件,3,xiaowang]"));
    }

    @Test
    void insertItemIntoListWithDifferentUserNameButNoEventName() throws Exception {
        UserDto userDto = new UserDto("dawang",19,"female","a@thoughtworks.com","18888888888");
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[第一条事件,1,xiaowang, 第二条事件,2,xiaowang, 第三条事件,3,xiaowang]"));
        RsItem rsItem = new RsItem("", "4",userDto);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(rsItem);
        mockMvc.perform(post("/rs/all").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
//        mockMvc.perform(get("/user/all"))
//                .andExpect(content().string("[]"));
//        String jsonUser = objectMapper.writeValueAsString(userDto);
//        mockMvc.perform(post("/user/register")
//                .content(jsonUser).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//        mockMvc.perform(get("/user/all"))
//                .andExpect(content().string("[userName=dawang]"));
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[第一条事件,1,xiaowang, 第二条事件,2,xiaowang, 第三条事件,3,xiaowang]"));
    }
    @Test
    void insertItemIntoListWithDifferentUserNameButNoEventKeyWord() throws Exception {
        UserDto userDto = new UserDto("dawang",19,"female","a@thoughtworks.com","18888888888");
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[第一条事件,1,xiaowang, 第二条事件,2,xiaowang, 第三条事件,3,xiaowang]"));
        RsItem rsItem = new RsItem("第四条事件", "",userDto);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(rsItem);
        mockMvc.perform(post("/rs/all").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
//        mockMvc.perform(get("/user/all"))
//                .andExpect(content().string("[]"));
//        String jsonUser = objectMapper.writeValueAsString(userDto);
//        mockMvc.perform(post("/user/register")
//                .content(jsonUser).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//        mockMvc.perform(get("/user/all"))
//                .andExpect(content().string("[userName=dawang]"));
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[第一条事件,1,xiaowang, 第二条事件,2,xiaowang, 第三条事件,3,xiaowang]"));
    }

    @Test
    void insertItemIntoListWithNoUser() throws Exception {
        UserDto userDto = new UserDto("dawang",19,"female","a@thoughtworks.com","18888888888");
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[第一条事件,1,xiaowang, 第二条事件,2,xiaowang, 第三条事件,3,xiaowang]"));
        RsItem rsItem = new RsItem("第四条事件", "4",null);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(rsItem);
        mockMvc.perform(post("/rs/all").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
//        mockMvc.perform(get("/user/all"))
//                .andExpect(content().string("[]"));
//        String jsonUser = objectMapper.writeValueAsString(userDto);
//        mockMvc.perform(post("/user/register")
//                .content(jsonUser).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//        mockMvc.perform(get("/user/all"))
//                .andExpect(content().string("[userName=dawang]"));
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[第一条事件,1,xiaowang, 第二条事件,2,xiaowang, 第三条事件,3,xiaowang]"));
    }


    @Test
    void fixItemInList() throws Exception {
        UserDto userDto = new UserDto("xiaowang",19,"female","a@thoughtworks.com","18888888888");

        ObjectMapper objectMapper = new ObjectMapper();

        RsItem noNameRsItem = new RsItem(null, "修改keyword-1",userDto);
        String noNameJson = objectMapper.writeValueAsString(noNameRsItem);
        mockMvc.perform(post("/rs/fix/0").content(noNameJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[第一条事件,修改keyword-1,xiaowang, 第二条事件,2,xiaowang, 第三条事件,3,xiaowang]"));

        RsItem noKeywordRsItem = new RsItem("修改name-2", null,userDto);
        String noKeywordJson = objectMapper.writeValueAsString(noKeywordRsItem);
        mockMvc.perform(post("/rs/fix/0").content(noKeywordJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[修改name-2,修改keyword-1,xiaowang, 第二条事件,2,xiaowang, 第三条事件,3,xiaowang]"));

        RsItem fixRsItem = new RsItem("修改name-3", "修改keyword-3",userDto);
        String fixJson = objectMapper.writeValueAsString(fixRsItem);
        mockMvc.perform(post("/rs/fix/1").content(fixJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[修改name-2,修改keyword-1,xiaowang, 修改name-3,修改keyword-3,xiaowang, 第三条事件,3,xiaowang]"));
    }

    @Test
    void deleteItemInList() throws Exception {
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[第一条事件,1,xiaowang, 第二条事件,2,xiaowang, 第三条事件,3,xiaowang]"));
        mockMvc.perform(get("/rs/delete/1")).andExpect(status().isOk());
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[第一条事件,1,xiaowang, 第三条事件,3,xiaowang]"));
        mockMvc.perform(get("/rs/delete/2")).andExpect(status().isOk());
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[第一条事件,1,xiaowang, 第三条事件,3,xiaowang]"));
    }


}

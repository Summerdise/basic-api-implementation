package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.Entity.RsEventEntity;
import com.thoughtworks.rslist.Entity.UserEntity;
import com.thoughtworks.rslist.Repository.RsEventRepository;
import com.thoughtworks.rslist.Repository.UserRepository;
import com.thoughtworks.rslist.dto.RsItem;
import com.thoughtworks.rslist.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RsControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup(){
        userRepository.deleteAll();
        rsEventRepository.deleteAll();
    }
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
        String json = objectMapper.writeValueAsString(rsItem);
        mockMvc.perform(post("/rs/all").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/users"))
                .andExpect(content().string("[]"));
        String jsonUser = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/user/add")
                .content(jsonUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/users"))
                .andExpect(content().string("[{\"user_name\": \"dawang\",\"user_age\":19, \"user_gender\": \"female\", \"user_email\":a@thoughtworks.com, \"user_phone\": \"18888888888\"}]"));
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[第一条事件,1,xiaowang, 第二条事件,2,xiaowang, 第三条事件,3,xiaowang]"));
    }

    @Test
    void insertItemIntoListWithDifferentUserNameButNoEventName() throws Exception {
        UserDto userDto = new UserDto("dawang",19,"female","a@thoughtworks.com","18888888888");
        mockMvc.perform(get("/rs/all"))
                .andExpect(content().string("[第一条事件,1,xiaowang, 第二条事件,2,xiaowang, 第三条事件,3,xiaowang]"));
        RsItem rsItem = new RsItem("", "4",userDto);
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


    //新
    @Test
    public void shouldAddRsEvent() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .userName("user_1")
                .gender("male")
                .age(22)
                .email("a@thoushtworks.com")
                .phone("12345678912")
                .voteNum(20)
                .build();
        userRepository.save(userEntity);
        RsEventEntity rsEntity = RsEventEntity.builder()
                .eventName("event1")
                .keyword("news")
                .userId(1)
                .build();
        rsEventRepository.save(rsEntity);
        String rsEntityJson = objectMapper.writeValueAsString(rsEntity);
        mockMvc.perform(post("/rs/event")
                .content(rsEntityJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        List<RsEventEntity> rsEvents =rsEventRepository.findAll();
        assertEquals(1,rsEvents.size());
        assertEquals("event1",rsEvents.get(0).getEventName());
    }
    @Test
    public void shouldAddRsEventWhenUserIsNotExsit() throws Exception {
        RsEventEntity rsEntity = RsEventEntity.builder()
                .eventName("event1")
                .keyword("news")
                .userId(1)
                .build();
        String rsEntityJson = objectMapper.writeValueAsString(rsEntity);
        mockMvc.perform(post("/rs/event")
                .content(rsEntityJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        List<RsEventEntity> rsEvents =rsEventRepository.findAll();
        assertEquals(0,rsEvents.size());
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        UserEntity userEntity = UserEntity.builder()
                .userName("user_1")
                .gender("male")
                .age(22)
                .email("a@thoushtworks.com")
                .phone("12345678912")
                .voteNum(20)
                .build();
        userRepository.save(userEntity);
        RsEventEntity rsEntity = RsEventEntity.builder()
                .eventName("event1")
                .keyword("news")
                .userId(1)
                .build();
        rsEventRepository.save(rsEntity);

        mockMvc.perform(delete("/rs/delete/{id}",userEntity.getId()))
                .andExpect(status().isNoContent());

        List<UserEntity> users = userRepository.findAll();
        List<RsEventEntity> rsEvents = rsEventRepository.findAll();

        assertEquals(0,users.size());
        assertEquals(0,rsEvents.size());

    }

}

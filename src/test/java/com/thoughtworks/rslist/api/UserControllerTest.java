package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.Entity.RsEventEntity;
import com.thoughtworks.rslist.Entity.UserEntity;
import com.thoughtworks.rslist.Repository.RsEventRepository;
import com.thoughtworks.rslist.Repository.UserRepository;
import com.thoughtworks.rslist.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;

    ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        objectMapper = new ObjectMapper();
        userRepository.deleteAll();
        rsEventRepository.deleteAll();
    }

    @Test
    void shouldRegisterUser() throws Exception {
        UserDto userDto = new UserDto("xiaowang", 19, "female", "a@thoughtworks.com", "18888888888");

        String userDtoJson = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/user/register")
                .content(userDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        List<UserEntity> userEntityList = userRepository.findAll();
        assertEquals(1, userEntityList.size());
        assertEquals("xiaowang", userEntityList.get(0).getUserName());
    }

    @Test
    void shouldDeleteAllUser() throws Exception {
        userRepository.deleteAll();
        mockMvc.perform(get("/user/delete")).andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }


    @Test
    void shouldDeleteUser() throws Exception {
        userRepository.deleteAll();
        mockMvc.perform(get("/user/delete")).andExpect(status().isOk())
                .andExpect(content().string("[]"));

        UserDto userDto = new UserDto("xiaowang", 19, "female", "a@thoughtworks.com", "18888888888");
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/user/register")
                .content(userDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        List<UserEntity> userEntityList = userRepository.findAll();
        assertEquals(1, userEntityList.size());
        assertEquals("xiaowang", userEntityList.get(0).getUserName());

        mockMvc.perform(get("/user/delete/1")).andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }


    @Test
    void shouldNotRegisterNullName() throws Exception {
        UserDto userDto = new UserDto("", 19, "female", "a@thoughtworks.com", "18888888888");
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/user/register")
                .content(userDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotRegisterLongName() throws Exception {
        UserDto userDto = new UserDto("sajhsajhlahdjadh", 19, "female", "a@thoughtworks.com", "18888888888");
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/user/register")
                .content(userDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotRegisterNoneAge() throws Exception {
        UserDto userDto = new UserDto("xiaowang", null, "female", "a@thoughtworks.com", "18888888888");
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/user/register")
                .content(userDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotRegisterLowAge() throws Exception {
        UserDto userDto = new UserDto("xiaowang", 13, "female", "a@thoughtworks.com", "18888888888");
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/user/register")
                .content(userDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotRegisterHighAge() throws Exception {
        UserDto userDto = new UserDto("xiaowang", 101, "female", "a@thoughtworks.com", "18888888888");
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/user/register")
                .content(userDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotRegisterNoneGender() throws Exception {
        UserDto userDto = new UserDto("xiaowang", 18, "", "a@thoughtworks.com", "18888888888");
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/user/register")
                .content(userDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotRegisterWrongEmail() throws Exception {
        UserDto userDto = new UserDto("xiaowang", 18, "female", "@thoughtworks.com", "18888888888");
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/user/register")
                .content(userDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotRegisterPhoneNotBeginWith1() throws Exception {
        UserDto userDto = new UserDto("xiaowang", 18, "female", "@thoughtworks.com", "88888888888");
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/user/register")
                .content(userDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotRegisterPhoneLessEleven() throws Exception {
        UserDto userDto = new UserDto("xiaowang", 18, "female", "@thoughtworks.com", "188888888");
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/user/register")
                .content(userDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotRegisterPhoneMoreEleven() throws Exception {
        UserDto userDto = new UserDto("xiaowang", 18, "female", "@thoughtworks.com", "188888888888");
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/user/register")
                .content(userDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldDeleteUserById() throws Exception {
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

        mockMvc.perform(delete("/user/delete/{id}",userEntity.getId()))
                .andExpect(status().isNoContent());

        List<UserEntity> users = userRepository.findAll();
        List<RsEventEntity> rsEvents = rsEventRepository.findAll();

        assertEquals(0,users.size());
        assertEquals(0,rsEvents.size());
    }
}
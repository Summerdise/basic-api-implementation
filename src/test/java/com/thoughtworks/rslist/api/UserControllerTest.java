package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldRegisterUser() throws Exception {

        UserDto userDto = new UserDto("xiaowang",19,"female","a@thoughtworks.com","18888888888");

        ObjectMapper objectMapper = new ObjectMapper();
        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/user/register")
                .content(userDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotRegisterNullName() throws Exception {
        UserDto userDto = new UserDto("",19,"female","a@thoughtworks.com","18888888888");

        ObjectMapper objectMapper = new ObjectMapper();
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/user/register")
                .content(userDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotRegisterLongName() throws Exception {
        UserDto userDto = new UserDto("sajhsajhlahdjadh",19,"female","a@thoughtworks.com","18888888888");

        ObjectMapper objectMapper = new ObjectMapper();
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/user/register")
                .content(userDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotRegisterNoneAge() throws Exception {
        UserDto userDto = new UserDto("xiaowang",null,"female","a@thoughtworks.com","18888888888");

        ObjectMapper objectMapper = new ObjectMapper();
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/user/register")
                .content(userDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void shouldNotRegisterSmallName() throws Exception {
        UserDto userDto = new UserDto("xiaowang",13,"female","a@thoughtworks.com","18888888888");

        ObjectMapper objectMapper = new ObjectMapper();
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/user/register")
                .content(userDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void shouldNotRegisterBigName() throws Exception {
        UserDto userDto = new UserDto("xiaowang",101,"female","a@thoughtworks.com","18888888888");

        ObjectMapper objectMapper = new ObjectMapper();
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/user/register")
                .content(userDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    
}
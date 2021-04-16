package com.worldtravel.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worldtravel.demo.model.User;
import com.worldtravel.demo.security.MyUserDetailsService;
import com.worldtravel.demo.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
class UserControllerTest {
    static ApplicationContext applicationContext = null;

    @MockBean
    private UserService userService;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    private User user;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup(){
        user = new User(1L, "jenjanik", "jen@scuz.com", "123456");
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void tearDown(){
        user = null;
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }


    @Test
    void createUserTest() throws Exception {
        when(userService.createUser(any())).thenReturn(user);
        mockMvc.perform(post("/auth/users/register").
                contentType(MediaType.APPLICATION_JSON).
                content(mapToJson(user))).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void loginUser() throws Exception{
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);
        when(userService.loginUser(any())).thenReturn(response);
        mockMvc.perform(post("/auth/users/login")
               .contentType(MediaType.APPLICATION_JSON).content(mapToJson(user)))
               .andExpect(status().isOk());
    }

    @Test
    void updatePassword() throws Exception{
        String response  = "update successful";
        when(userService.updatePassword(any())).thenReturn(response);
        mockMvc.perform((put("/auth/users/reset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(user))))
                .andExpect(status().isOk());
    }

}

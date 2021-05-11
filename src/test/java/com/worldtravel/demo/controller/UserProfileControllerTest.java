package com.worldtravel.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worldtravel.demo.model.User;
import com.worldtravel.demo.model.UserProfile;
import com.worldtravel.demo.security.MyUserDetailsService;
import com.worldtravel.demo.service.UserProfileService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)

class UserProfileControllerTest {
    static ApplicationContext applicationContext = null;
    ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);

    @MockBean
    private UserProfileService userProfileService;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    private User user;

    private UserProfile userProfile;

    @InjectMocks
    private UserProfileController userProfileController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        user = new User(1L, "jenjanik", "jen@scuz.com", "123456");
        userProfile = new UserProfile(1L, "Jen", "Janik", "Jen's profile");
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void tearDown(){
        user = null;
        userProfile = null;
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    @Test
    void createUserProfile() throws Exception {
        when(userProfileService.createUserProfile(any())).thenReturn(String.valueOf(response));
        mockMvc.perform(post("/auth/api/profile").
                contentType(MediaType.APPLICATION_JSON).
                content(mapToJson(user))).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateUserProfile() throws Exception {
        when(userProfileService.updateUserProfile(any())).thenReturn(userProfile);
        mockMvc.perform(post("/auth/api/profile").
                contentType(MediaType.APPLICATION_JSON).
                content(mapToJson(user))).
                andExpect(MockMvcResultMatchers.status().isOk());
    }
}


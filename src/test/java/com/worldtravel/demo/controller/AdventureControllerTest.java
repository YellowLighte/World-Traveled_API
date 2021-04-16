package com.worldtravel.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worldtravel.demo.model.Adventure;
import com.worldtravel.demo.model.Country;
import com.worldtravel.demo.model.User;
import com.worldtravel.demo.model.UserProfile;
import com.worldtravel.demo.repository.AdventureRepository;
import com.worldtravel.demo.repository.CountryRepository;
import com.worldtravel.demo.security.MyUserDetailsService;
import com.worldtravel.demo.service.AdventureService;
import com.worldtravel.demo.service.UserProfileService;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.experimental.results.ResultMatchers;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
class AdventureControllerTest {
    static ApplicationContext applicationContext = null;
    ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);

    @MockBean
    private AdventureService adventureService;

    @Autowired
    MyUserDetailsService myUserDetailsService;


    private User user;
    private Adventure adventure1;
    private Adventure adventure2;
    private Country mexico;
    private Country germany;
    private List<Adventure> adventureList;

    @InjectMocks
    private AdventureController adventureController;
//    @InjectMocks
//    private CountryRepository countryRepository;
//    @InjectMocks
//    private AdventureRepository adventureRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup(){
        user = new User(1L, "jenjanik", "jen@scuz.com", "123456");
        mexico = new Country(1L, "Mexico");
        germany = new Country(2L, "Germany");
        adventureList = new ArrayList<>();
        adventure1 = new Adventure(1L, "Adventure 1", "12/1/20", "Fun trip to Mexico", "Mexico");
        adventure2 = new Adventure(1L, "Adventure 2", "12/1/21", "Fun trip to Germany", "Germany");
        adventureList.add(adventure1);
        adventureList.add(adventure2);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void tearDown(){
        user = null;
        adventure1 = null;
        adventure2 = null;
        mexico = null;
        germany = null;
        adventureList = null;
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    //TODO: Ask Suresh, Thiago, and Marc if this is actually utilizing the controller since I'm creating adventureList and passing it in .thenReturn() below
    @Test
    void getAdventures() throws Exception{
        when(adventureService.getAdventures()).thenReturn(adventureList);
        mockMvc.perform(get("/api/adventures").
                contentType(MediaType.APPLICATION_JSON).
                content(mapToJson(adventureList))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                // Checks that the list has a size of 2 and the values of attributes of Adventure in the 0th index matches expected
                andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id").value(1L)).
                andExpect(jsonPath("$[0].adventureName").value(adventure1.getAdventureName())).
                andExpect(jsonPath("$[0].dateWent").value(adventure1.getDateWent())).
                andExpect(jsonPath("$[0].adventureDescription").value(adventure1.getAdventureDescription())).
                andExpect(jsonPath("$[0].countryName").value(adventure1.getCountryName())).andDo(print());
    }

    @Test
    void getAdventure() throws Exception{
        when(adventureService.getAdventure(any())).thenReturn(adventure1);
        mockMvc.perform(get("/api/adventures/{id}", 1).
                contentType(MediaType.APPLICATION_JSON).
                content(mapToJson(adventure2))).
                andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1)).andDo(print());
    }

    @Test
    void createAdventure() {
    }

    @Test
    void getCountries() {
    }

    @Test
    void getCountry() {
    }

    @Test
    void deleteAdventure() {
    }

    @Test
    void updateAdventure() {
    }
}
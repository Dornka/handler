package com.example.backend.controller;

import com.example.backend.model.Address;
import com.example.backend.model.Person;
import com.example.backend.repository.PersonRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    PersonRepo personRepo;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DirtiesContext
    void getAllPersons() throws Exception {
        // GIVEN
        Address address = new Address("12345", "CityName", "StreetName", "42");
        Person person = new Person("1", "firstName", "lastName", address);
        personRepo.save(person);

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/persons"))
                //THEN
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
            [
                {
                    "id": "1",
                    "firstName": "firstName",
                    "lastName": "lastName",
                    "address": {
                        "addressPLZ": "12345",
                        "addressCity": "CityName",
                        "addressStreet": "StreetName",
                        "addressHouseNumber": "42"
                    }
                }
            ]
        """));
    }

    @DirtiesContext
    @Test
    void postPerson() throws Exception {
        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/api/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "firstName": "firstName",
                    "lastName": "lastName",
                    "address": {
                        "addressPLZ": "12345",
                        "addressCity": "CityName",
                        "addressStreet": "StreetName",
                        "addressHouseNumber": "42"
                    }
                }
        """))
                //THEN
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                {
                    "firstName": "firstName",
                    "lastName": "lastName",
                    "address": {
                        "addressPLZ": "12345",
                        "addressCity": "CityName",
                        "addressStreet": "StreetName",
                        "addressHouseNumber": "42"
                    }
                }
            
        """))
                .andExpect(jsonPath("$.id").isNotEmpty());

    }
}

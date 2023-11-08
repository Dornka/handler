package com.example.backend.controller;

import com.example.backend.model.Address;
import com.example.backend.model.Person;
import com.example.backend.repository.PersonRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
        mockMvc.perform(post("/api/persons")
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

    @Test
    @DirtiesContext
    void deleteRecipeById() throws Exception {
        //GIVEN
        Address address = new Address("12345", "CityName", "StreetName", "42");
        Person person = new Person("1", "firstName", "lastName", address);
        personRepo.save(person);
        //WHEN
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/persons/1"))
                //THEN
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    @DirtiesContext
    void findPersonById() throws Exception {
        //GIVEN
        String testBody = mockMvc.perform(post("/api/persons")
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
        """)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Person response = objectMapper.readValue(testBody, Person.class);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/persons/" + response.id()))

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

    @Test
    @DirtiesContext
    void updatePerson() throws Exception {
        //GIVEN
        Address address = new Address("12345", "CityName", "StreetName", "42");
        Person person = new Person("1", "firstName", "lastName", address);
        personRepo.save(person);
        //WHEN
        mockMvc.perform(put("/api/persons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            
                {
                    "id": "1",
                    "firstName": "firstName2222",
                    "lastName": "lastName",
                    "address": {
                        "addressPLZ": "12345",
                        "addressCity": "CityName",
                        "addressStreet": "StreetName",
                        "addressHouseNumber": "42"
                    }
                }
            
        """
                        ))
                //THEN
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
            
                {
                     "id": "1",
                    "firstName": "firstName2222",
                    "lastName": "lastName",
                    "address": {
                        "addressPLZ": "12345",
                        "addressCity": "CityName",
                        "addressStreet": "StreetName",
                        "addressHouseNumber": "42"
                    }
                }
            
        """));
    }

    @DirtiesContext
    @Test
    void PostPersonWithInvalidData() throws Exception{
        //GIVEN
        Address address = new Address("12345", "CityName", "StreetName", "42");
        Person person = new Person("1", "firstName", "lastName", address);
        personRepo.save(person);
        //WHEN
        mockMvc.perform(post("/api/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
{
                    "firstName": "",
                    "lastName": "",
                    "address": {
                        "addressPLZ": "",
                        "addressCity": "",
                        "addressStreet": "",
                        "addressHouseNumber": ""
                    }
                }
"""))
                //THEN
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Bitte alles ausfüllen."));
    }

    @DirtiesContext
    @Test
    void expectSuccessfulPost() throws Exception{
        //WHEN
        String actual = mockMvc.perform(
                post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""  
                {
                    "firstName": "firstName2222",
                    "lastName": "lastName",
                    "address": {
                        "addressPLZ": "12345",
                        "addressCity": "CityName",
                        "addressStreet": "StreetName",
                        "addressHouseNumber": "42"
                    }
                }"""
                        )
        )
                //THEN
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                    {
                    "firstName": "firstName2222",
                    "lastName": "lastName",
                    "address": {
                        "addressPLZ": "12345",
                        "addressCity": "CityName",
                        "addressStreet": "StreetName",
                        "addressHouseNumber": "42"
                    }
                }"""
                ))
                .andReturn()
                .getResponse()
                .getContentAsString();

                Person actualPerson = objectMapper.readValue(actual, Person.class);
                assertThat(actualPerson.id())
                        .isNotBlank();
    }
    @Test
    @DirtiesContext
    void testUpdatePersonWithMismatchedIds() throws JsonProcessingException {
        // GIVEN
        String idInPath = "1";
        Person person = new Person("2", "firstName", "lastName", new Address("12345", "CityName", "StreetName", "42"));
        String requestContent = objectMapper.writeValueAsString(person);

        // WHEN and THEN
        Exception actualException = assertThrows(ServletException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders.put("/api/persons/" + idInPath)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestContent));
        });

        // Unwrap and check the actual cause
        Throwable rootCause = actualException.getCause();
        assertThat(rootCause).isInstanceOf(IllegalArgumentException.class);
    }
}


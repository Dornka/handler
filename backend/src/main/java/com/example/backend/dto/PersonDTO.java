package com.example.backend.dto;

import com.example.backend.model.Address;


public record PersonDTO(

        String id,
        String firstName,
        String lastName,

        Address address

) {

}

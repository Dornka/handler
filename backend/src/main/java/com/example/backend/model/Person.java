package com.example.backend.model;


public record Person(

        String id,
        String firstName,
        String lastName,

        Address address

) {
}

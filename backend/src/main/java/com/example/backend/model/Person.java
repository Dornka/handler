package com.example.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("persons")
public record Person(

        @MongoId
        String id,
        String firstName,
        String lastName,

        Address address

) {
}

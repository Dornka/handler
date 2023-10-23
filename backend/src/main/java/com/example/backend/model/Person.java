package com.example.backend.model;

public record Person(

        String id,
        String firstName,
        String lastName,
        String addressPLZ,
        String addressCity,
        String addressStreet,
        String addressHouseNumber

) {
}

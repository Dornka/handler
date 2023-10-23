package com.example.backend.model;

public record NewPerson(

        String firstName,
        String lastName,
        String addressPLZ,
        String addressCity,
        String addressStreet,
        String addressHouseNumber

) {
}
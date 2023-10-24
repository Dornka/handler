package com.example.backend.model;

public record Address(

        String addressPLZ,
        String addressCity,
        String addressStreet,
        String addressHouseNumber
) {
}

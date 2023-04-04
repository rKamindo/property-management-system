package com.randy.propertymanagementsystem.apartment;

import org.springframework.lang.NonNull;

public record CreateApartmentRequest(
        @NonNull
        Long propertyId,
        @NonNull
        String apartmentNumber,
        double rent
) {

}
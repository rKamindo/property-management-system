package com.randy.propertymanagementsystem.apartment;

public record UpdateApartmentRequest(
        String apartmentNumber,
        int numberOfRooms,
        double rent
) {
}

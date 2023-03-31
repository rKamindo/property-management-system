package com.randy.propertymanagementsystem.apartment;

public record ApartmentCreateRequest (
        String apartmentNumber,
        int numberOfRooms,
        double rent,
        boolean isAvailable,
        boolean isOccupied
) {

}
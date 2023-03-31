package com.randy.tenantmanagementsystem.apartment;

public record ApartmentCreateRequest (
        String apartmentNumber,
        int numberOfRooms,
        double rent,
        boolean isAvailable,
        boolean isOccupied
) {

}
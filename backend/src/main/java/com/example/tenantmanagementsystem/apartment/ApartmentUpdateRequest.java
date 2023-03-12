package com.example.tenantmanagementsystem.apartment;

public record ApartmentUpdateRequest(
        String apartmentNumber,
        int numberOfRooms,
        double rent,
        boolean isOccupied,
        boolean isAvailable
) {
}

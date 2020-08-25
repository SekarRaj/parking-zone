package org.gojek.entities.domain;

public interface ParkingLot {
    int getTotalCapacity();

    int getAvailableCapacity();

    int getOccupiedCapacity();

    int nextAvailableSlot();

    boolean parkCar(Car car);

    int leaveParking(Car car);
}


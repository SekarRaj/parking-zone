package org.gojek.entities.domain;

import java.util.List;

public interface ParkingLot {
    int getTotalCapacity();

    int getAvailableCapacity();

    int nextAvailableSlot();

    boolean parkCar(Car car);

    int leaveParking(Car car);

    List<String> getRegNumberOfCarsWithColor(String color);

    int getSlotNumberOfCarWithRegistrationNumber(Car car);

    List<Integer> getSlotNumberOfCarWithColor(String color);
}


package org.gojek.entities.domain;

import java.util.List;

public interface ParkingLot {
    int getTotalCapacity();

    int getAvailableCapacity();

    int nextAvailableSlot();

    String parkCar(Car car);

    int leaveParking(int slotNumber);

    List<String> getRegNumberOfCarsWithColor(String color);

    int getSlotNumberOfCarWithRegistrationNumber(Car car);

    List<Integer> getSlotNumberOfCarWithColor(String color);

    String status();
}


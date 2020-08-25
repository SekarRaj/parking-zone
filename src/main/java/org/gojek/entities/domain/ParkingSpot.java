package org.gojek.entities.domain;

public class ParkingSpot {
    private int spotNumber;

    private Car car;

    private ParkingSpot(int spotNumber, Car car) {
        this.spotNumber = spotNumber;
        this.car = car;
    }

    public static ParkingSpot withSpotNumberAndCar(int spotNumber, Car car) {
        return new ParkingSpot(spotNumber, car);
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public Car getCar() {
        return car;
    }
}

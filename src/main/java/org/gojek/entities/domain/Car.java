package org.gojek.entities.domain;

public class Car implements Vehicle {
    private final String registrationNumber;
    private final String color;

    private Car(String registrationNumber, String color) {
        this.registrationNumber = registrationNumber;
        this.color = color;
    }

    public static Car withRegistrationNumberAndColor(String registrationNumber, String color) {
        assert (registrationNumber.length() > 0) : "Invalid Registration Number";
        return new Car(registrationNumber, color);
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getColor() {
        return color;
    }
}

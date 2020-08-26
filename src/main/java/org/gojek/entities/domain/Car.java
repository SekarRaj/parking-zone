package org.gojek.entities.domain;

public class Car{
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

    public static Car withRegistrationNumber(String registrationNumber) {
        return new Car(registrationNumber, "Unknown");
    }

    public static Car withColor(String color) {
        return new Car("Unknown", color);
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getColor() {
        return color;
    }
}

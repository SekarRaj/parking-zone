package org.gojek

import org.gojek.entities.domain.Car
import org.gojek.entities.domain.ParkingLotImpl
import spock.lang.Specification

class MaintainParkingLotSpec extends Specification {
    def initialCapacity = 5;
    def lot = ParkingLotImpl.withCapacity(initialCapacity)

    def "when no matching car color is present, then no cars should be returned"() {
        given:
        lot.availableCapacity == initialCapacity
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-1234", "White"))

        when:
        List<String> regNumbers = lot.getRegNumberOfCarsWithColor("Black")

        then:
        regNumbers.size() == 0
    }

    def "when matching car color is present, then the car's registration number should be returned"() {
        given:
        lot.availableCapacity == initialCapacity
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-1234", "White"))
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-BB-0001", "Black"))

        when:
        List<String> regNumbers = lot.getRegNumberOfCarsWithColor("White")

        then:
        regNumbers.size() == 1
        regNumbers.contains("KA-01-HH-1234")
    }

    def "when multiple matching cars are present, then all car's registration number should be returned"() {
        given:
        lot.availableCapacity == initialCapacity
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-1234", "White"))
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-BB-0001", "Black"))
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-9999", "White"))

        when:
        List<String> regNumbers = lot.getRegNumberOfCarsWithColor("White")

        then:
        regNumbers.size() == 2
        regNumbers.equals(["KA-01-HH-1234", "KA-01-HH-9999"])
    }

    def "should return slot number of car for the give registration number"() {
        given:
        lot.availableCapacity == initialCapacity
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-1234", "White"))
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-BB-0001", "Black"))
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-9999", "White"))

        when:
        int slotNumber =
                lot.getSlotNumberOfCarWithRegistrationNumber(Car.withRegistrationNumber("KA-01-BB-0001"))

        then:
        slotNumber == 2
    }

    def "should return slot numbers of all cars for the given color"() {
        given:
        lot.availableCapacity == initialCapacity
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-1234", "White"))
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-BB-0001", "Black"))
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-9999", "White"))

        when:
        List<Integer> slotNumbers = lot.getSlotNumberOfCarWithColor("White")

        then:
        slotNumbers.size() == 2
    }
}

package org.gojek

import org.gojek.entities.domain.Car
import org.gojek.entities.domain.ParkingLotImpl
import spock.lang.Specification

class OperateParkingLotSpec extends Specification {
    def initialCapacity = 5;
    def lot = ParkingLotImpl.withCapacity(initialCapacity)

    def "should have parking space after lot creation"() {
        given:
        lot.availableCapacity == initialCapacity

        when:
        def parked = lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-1234", "White"))

        then:
        parked
    }

    def "available capacity must be reduced when a car is parked"() {
        given:
        lot.availableCapacity == initialCapacity

        when:
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-1234", "White"))

        then:
        lot.availableCapacity != lot.totalCapacity
        lot.availableCapacity == (lot.totalCapacity - 1)
    }

    def "should throw exception when capacity is full and a car is trying to park"() {
        def lot = ParkingLotImpl.withCapacity(1)
        given:
        lot.availableCapacity == initialCapacity
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-1234", "White"))

        when:
        lot.nextAvailableSlot() == 0
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-9999", "White"))

        then:
        def err = thrown(AssertionError)
        err.message == "Sorry, parking lot is full"
    }

    def "should take up the first available spot"() {
        given:
        lot.availableCapacity == initialCapacity

        when:
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-1234", "White"))
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-9999", "White"))

        then:
        lot.nextAvailableSlot() == 3
    }

    def "free space must be reused in the available order"() {
        given:
        lot.availableCapacity == initialCapacity

        when:
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-1234", "White"))
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-9999", "White"))
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-BB-0001", "Black"))
        lot.leaveParking(1);

        then:
        lot.nextAvailableSlot() == 1
    }

    def "When cars inbetween leaves those parking lots must be used in sequence"() {
        given:
        lot.availableCapacity == initialCapacity

        when:
        def firstCar = Car.withRegistrationNumberAndColor("KA-01-HH-1234", "White")
        def secondCar = Car.withRegistrationNumberAndColor("KA-01-HH-9999", "White")
        def thirdCar = Car.withRegistrationNumberAndColor("KA-01-BB-0001", "Black")
        lot.parkCar(firstCar)
        lot.parkCar(secondCar)
        lot.parkCar(thirdCar);
        lot.leaveParking(2);

        then:
        lot.nextAvailableSlot() == 2
        lot.availableCapacity == 3
    }
}

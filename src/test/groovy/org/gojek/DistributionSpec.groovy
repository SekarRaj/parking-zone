package org.gojek

import org.gojek.entities.domain.Car
import org.gojek.entities.domain.ParkingLotImpl
import org.gojek.entities.domain.ParkingZone
import spock.lang.Specification

class DistributionSpec extends Specification {
    def "can park car in the zone"() {
        given:
        def zone = new ParkingZone();
        def firstLot = ParkingLotImpl.withCapacity(2)
        def secondLot = ParkingLotImpl.withCapacity(2)
        zone.addLot(firstLot)
        zone.addLot(secondLot)

        when:
        Car firstCar = Car.withRegistrationNumber("AC01 KA 1234")
        Car secondCar = Car.withRegistrationNumber("AC01 KA 1235")
        zone.parkCar(firstCar) //First lot - first spot
        zone.parkCar(secondCar) //Second lot - first spot

        then:
        firstLot.availableCapacity == 1
        firstLot.nextAvailableSlot() == 2

        secondLot.availableCapacity == 1
        secondLot.nextAvailableSlot() == 2
    }

    def "change distribution"() {
        given:
        def zone = new ParkingZone()

        when:
        zone.changeDistributionStrategy("fill-first")
        def strategy = zone.currentStrategy()

        then:
        strategy == "Fill First"
    }

    def "cars should filled in sequential lots"() {
        given:
        def zone = new ParkingZone();
        def firstLot = ParkingLotImpl.withCapacity(2)
        def secondLot = ParkingLotImpl.withCapacity(2)
        zone.addLot(firstLot)
        zone.addLot(secondLot)
        zone.changeDistributionStrategy("fill-first")

        when:
        Car firstCar = Car.withRegistrationNumber("AC01 KA 1234")
        Car secondCar = Car.withRegistrationNumber("AC01 KA 1235")
        zone.parkCar(firstCar) //First lot - first spot
        zone.parkCar(secondCar) //First lot - second spot

        then:
        firstLot.availableCapacity == 0
        firstLot.nextAvailableSlot() == 0

        secondLot.availableCapacity == 2
        secondLot.nextAvailableSlot() == 1
    }

    def "car goes into zone based on strategy change at run time"() {
        given:
        def zone = new ParkingZone();
        def firstLot = ParkingLotImpl.withCapacity(3)
        def secondLot = ParkingLotImpl.withCapacity(3)
        zone.addLot(firstLot)
        zone.addLot(secondLot)

        /**
         * First two cars - Lot 1 and Lot 2
         * Change fill first
         * Next two cars first lot - Lot 1 - Lot 1
         * Next car should second lot - Lot 2
         */
        when: //Even dist
        Car firstCar = Car.withRegistrationNumber("AC01 KA 1234")
        Car secondCar = Car.withRegistrationNumber("AC02 KA 1235")
        zone.parkCar(firstCar)
        zone.parkCar(secondCar)

        then:
        firstLot.availableCapacity == 2
        secondLot.availableCapacity == 2

        when: //Fill dist
        zone.changeDistributionStrategy("fill-first")
        Car thirdCar = Car.withRegistrationNumber("AC03 KA 1234")
        Car fourthCar = Car.withRegistrationNumber("AC04 KA 1235")
        zone.parkCar(thirdCar)
        zone.parkCar(fourthCar)

        then:
        secondLot.availableCapacity == 2
        firstLot.availableCapacity == 0

        when:
        Car fifthCar = Car.withRegistrationNumber("AC05 KA 1234")
        zone.parkCar(fifthCar)

        then:
        firstLot.availableCapacity == 0
        secondLot.availableCapacity == 1

    }
}

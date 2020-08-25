package org.gojek

import org.gojek.entities.domain.ParkingLot

class FillParkingLotSpec {
    def initialCapacity = 5;
    def lot = ParkingLot.withCapacity(initialCapacity)

    def "should have parking space when empty"() {
        given:
        lot.availableCapacity == initialCapacity

        when:
        lot.parkVehicle(car)
    }
}

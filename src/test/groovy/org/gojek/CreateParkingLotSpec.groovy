package org.gojek

import org.gojek.entities.domain.ParkingLotImpl
import spock.lang.Specification

class CreateParkingLotSpec extends Specification {
    def "create a parking lot with capacity"() {
        expect:
        lot.totalCapacity == initCapacity

        where:
        lot                            || initCapacity
        ParkingLotImpl.withCapacity(6) || 6
        ParkingLotImpl.withCapacity(4) || 4
    }

    def "creating lot with invalid capacity should fail"() {
        when:
        ParkingLotImpl.withCapacity(-1)

        then:
        def err = thrown(expectedException)
        err.message == errorMessage

        where:
        capacity || expectedException || errorMessage
        -1       || AssertionError    || "Capacity should be at min 1 and max 100"
        0        || AssertionError    || "Capacity should be at min 1 and max 100"
        101      || AssertionError    || "Capacity should be at min 1 and max 100"
    }

    def "free capacity must be equal to available capacity upon initialization"() {
        expect:
        lot.availableCapacity == initCapacity

        where:
        lot                            || initCapacity
        ParkingLotImpl.withCapacity(6) || 6
        ParkingLotImpl.withCapacity(4) || 4
    }

    def "next available slot must be the 1st slot"() {
        expect:
        ParkingLotImpl.withCapacity(initCapacity as int).nextAvailableSlot() == nextSlot

        where:
        initCapacity << 6
        nextSlot << 1
    }

    def "occupied capacity must be equal to the initial capacity"() {
        expect:
        ParkingLotImpl.withCapacity(initCapacity as int).availableCapacity == initCapacity

        where:
        initCapacity << 6
    }
}

package org.gojek

import org.gojek.entities.domain.Car
import org.gojek.entities.domain.ParkingLotImpl
import spock.lang.Specification

class ReportingParkingLotSpec extends Specification {
    def initialCapacity = 5;
    def lot = ParkingLotImpl.withCapacity(initialCapacity)

    def "should return parking status of all currently parked vehicles"() {
        given:
        lot.availableCapacity == initialCapacity

        when:
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-1234", "White"))
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-9999", "White"))
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-BB-0001", "Black"))
        lot.leaveParking(1)
        def status = lot.status()

        then:
        status.size() > 0
        println(status)
    }
}

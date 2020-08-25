package org.gojek

import org.gojek.entities.domain.Car
import org.gojek.entities.domain.ParkingLotImpl
import spock.lang.Specification

class CompleteSpec extends Specification {

    def "End to end specification"() {
        given:
        def lot = ParkingLotImpl.withCapacity(6)
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-1234", "White"))
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-9999", "White"))
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-BB-0001", "Black"))
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-7777", "Red"))
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-2701", "Blue"))
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-3141", "Black"))

        when:
        lot.leaveParking(4)
        def status = lot.status()
        lot.parkCar(Car.withRegistrationNumberAndColor("KA-01-P-333", "White"))
        lot.parkCar(Car.withRegistrationNumberAndColor("DL-12-AA-9999", "White"))

        then:
        status.size() > 0
        println(status)
        def err = thrown(AssertionError)
        err.message == "Sorry, parking lot is full"
    }

}

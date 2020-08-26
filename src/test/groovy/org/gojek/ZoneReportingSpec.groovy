package org.gojek

import org.gojek.entities.domain.Car
import org.gojek.entities.domain.ParkingLotImpl
import org.gojek.entities.domain.ParkingZone
import spock.lang.Specification

class ZoneReportingSpec extends Specification{
    def "should support zone level color wise reporting"(){
        given:
        def zone = new ParkingZone()
        def firstLot = ParkingLotImpl.withCapacity(2)
        def secondLot = ParkingLotImpl.withCapacity(2)
        zone.addLot(firstLot)
        zone.addLot(secondLot)

        when:
        zone.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-1234", "White"))
        zone.parkCar(Car.withRegistrationNumberAndColor("KA-01-BB-0001", "Black"))
        zone.parkCar(Car.withRegistrationNumberAndColor("KA-01-HH-9999", "White"))
        List<String> regNumbers = zone.getCarsByColor("White")

        then:
        regNumbers.size() == 2
        println(regNumbers)
    }
}

package org.gojek.entities.domain;

import java.util.Map;

public interface DistributionStrategy {
    String strategyName();
    ParkingLot distribute(Car car, Map<Integer, ParkingLot> lots);
}

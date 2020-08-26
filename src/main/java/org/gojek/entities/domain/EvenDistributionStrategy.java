package org.gojek.entities.domain;

import java.util.Comparator;
import java.util.Map;

public class EvenDistributionStrategy implements DistributionStrategy {
    private String STRATEGY_NAME = "Even Distribution";

    @Override
    public String strategyName() {
        return STRATEGY_NAME;
    }

    @Override
    public ParkingLot distribute(Car car, Map<Integer, ParkingLot> lots) {
        return lots.values().stream()
                .filter(l -> l.getAvailableCapacity() > 0)
                .max(Comparator.comparing(ParkingLot::getAvailableCapacity))
                .orElseThrow(() -> new RuntimeException("No lot is available"));
    }
}

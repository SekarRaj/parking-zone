package org.gojek.entities.domain;

import java.util.Map;

public class FillFirstDistributionStrategy implements DistributionStrategy {
    private String STRATEGY_NAME = "Fill First";

    @Override
    public String strategyName() {
        return STRATEGY_NAME;
    }

    @Override
    public ParkingLot distribute(Car car, Map<Integer, ParkingLot> lots) {
        return lots.values().stream()
                .filter(l -> l.getAvailableCapacity() > 0)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("All lots are full"));
    }
}

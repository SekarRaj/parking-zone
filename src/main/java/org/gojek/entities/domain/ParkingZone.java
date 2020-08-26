package org.gojek.entities.domain;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ParkingZone {
    private final Map<Integer, ParkingLot> lots = new TreeMap<>();
    private static int counter = 1;

    private DistributionStrategy distributionStrategy;

    public ParkingZone() {
        distributionStrategy = new EvenDistributionStrategy();
    }

    public ParkingLot addLot(ParkingLot lot) {
        return lots.put(counter++, lot);
    }

    public void changeDistributionStrategy(String rule) {
        if (rule.equals("even_distribution")) {
            distributionStrategy = new EvenDistributionStrategy();
        } else {
            distributionStrategy = new FillFirstDistributionStrategy();
        }
    }

    public String parkCar(Car car) {
        ParkingLot lot = distributionStrategy.distribute(car, lots);
        if (lot.getAvailableCapacity() <= 0) {
            throw new RuntimeException("All lots are full");
        }

        return lot.parkCar(car);
    }

    public String currentStrategy() {
        return distributionStrategy.strategyName();
    }

    public List<String> getCarsByColor(String color) {
        return lots.values().stream()
                .flatMap(l -> l.getRegNumberOfCarsWithColor(color).stream())
                .collect(Collectors.toList());
    }
}

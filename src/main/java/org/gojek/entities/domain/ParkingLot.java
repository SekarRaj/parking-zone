package org.gojek.entities.domain;

import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.rangeClosed;

public class ParkingLot {
    private static final int MIN_CAPACITY = 1;
    private static final int MAX_CAPACITY = 100;
    private final int capacity;
    private Map<String, ? extends Vehicle> occupiedSlots;

    private final Queue<Integer> availableSlots;

    private ParkingLot(int capacity) {
        this.capacity = capacity;
        this.availableSlots = rangeClosed(1, capacity).boxed().collect(Collectors.toCollection(PriorityQueue::new));
    }

    public static ParkingLot withCapacity(int capacity) {
        assert (capacity > MIN_CAPACITY && capacity <= MAX_CAPACITY) : "Capacity should be at min 1 and max 100";
        return new ParkingLot(capacity);
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableCapacity() {
        return availableSlots.size();
    }

    public int getOccupiedCapacity() {
        return capacity - availableSlots.size();
    }

    public int nextAvailableSlot() {
        return Optional.ofNullable(availableSlots.peek()).orElse(0);
    }
}

package org.gojek.entities.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.rangeClosed;

public class ParkingLotImpl implements ParkingLot {
    private static final int MIN_CAPACITY = 1;
    private static final int MAX_CAPACITY = 100;
    private final int capacity;
    private final Map<Integer, ParkingSpot> occupiedSlots = new HashMap<>();
    private final Map<String, ParkingSpot> occupiedSlotsByRegNumber = new HashMap<>();

    private final Queue<Integer> availableSlots;

    private ParkingLotImpl(int capacity) {
        this.capacity = capacity;
        this.availableSlots = rangeClosed(1, capacity).boxed().collect(Collectors.toCollection(PriorityQueue::new));
    }

    public static ParkingLot withCapacity(int capacity) {
        assert (capacity >= MIN_CAPACITY && capacity <= MAX_CAPACITY) : "Capacity should be at min 1 and max 100";
        return new ParkingLotImpl(capacity);
    }

    @Override
    public int getTotalCapacity() {
        return capacity;
    }

    @Override
    public int getAvailableCapacity() {
        return availableSlots.size();
    }

    @Override
    public int getOccupiedCapacity() {
        return capacity - availableSlots.size();
    }

    @Override
    public int nextAvailableSlot() {
        return Optional.ofNullable(availableSlots.peek()).orElse(0);
    }

    @Override
    public boolean parkCar(Car car) {
        assert getAvailableCapacity() > 0 : "Sorry, parking lot is full";
        synchronized (this) {
            int spotNumber = availableSlots.remove();
            ParkingSpot spot = ParkingSpot.withSpotNumberAndCar(spotNumber, car);
            occupiedSlots.put(spotNumber, spot);
            occupiedSlotsByRegNumber.put(car.getRegistrationNumber(), spot);
        }
        return true;
    }

    @Override
    public int leaveParking(Car car) {
        assert occupiedSlotsByRegNumber.containsKey(car.getRegistrationNumber()) : "Car is not in the parking lot";
        ParkingSpot spot;
        synchronized (this) {
            spot = occupiedSlotsByRegNumber.remove(car.getRegistrationNumber());
            occupiedSlots.remove(spot.getSpotNumber());
            availableSlots.add(spot.getSpotNumber());
        }

        return spot.getSpotNumber();
    }


}

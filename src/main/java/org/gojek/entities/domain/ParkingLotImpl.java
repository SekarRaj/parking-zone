package org.gojek.entities.domain;

import java.util.HashMap;
import java.util.List;
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
    public int nextAvailableSlot() {
        return Optional.ofNullable(availableSlots.peek()).orElse(0);
    }

    @Override
    public String parkCar(Car car) {
        assert getAvailableCapacity() > 0 : "Sorry, parking lot is full";
        int spotNumber;
        synchronized (this) {
            spotNumber = availableSlots.remove();
            ParkingSpot spot = ParkingSpot.withSpotNumberAndCar(spotNumber, car);
            occupiedSlots.put(spotNumber, spot);
            occupiedSlotsByRegNumber.put(car.getRegistrationNumber(), spot);
        }
        return String.format("Allocated slot number: %d", spotNumber);
    }

    @Override
    public int leaveParking(int slotNumber) {
        assert occupiedSlots.containsKey(slotNumber) : "Parking lot is empty";
        ParkingSpot spot;
        synchronized (this) {
            spot = occupiedSlots.remove(slotNumber);
            occupiedSlotsByRegNumber.remove(spot.getCar().getRegistrationNumber());
            availableSlots.add(spot.getSpotNumber());
        }

        return spot.getSpotNumber();
    }

    @Override
    public List<String> getRegNumberOfCarsWithColor(String color) {
        return occupiedSlots.values().stream()
                .map(ParkingSpot::getCar)
                .filter(c -> c.getColor().equals(color))
                .map(Car::getRegistrationNumber)
                .collect(Collectors.toList());
    }

    @Override
    public int getSlotNumberOfCarWithRegistrationNumber(Car car) {
        return occupiedSlotsByRegNumber.get(car.getRegistrationNumber()).getSpotNumber();
    }

    @Override
    public List<Integer> getSlotNumberOfCarWithColor(String color) {
        return occupiedSlots.values().stream()
                .filter(ps -> ps.getCar().getColor().equals(color))
                .map(ParkingSpot::getSpotNumber)
                .collect(Collectors.toList());
    }

    @Override
    public String status() {
        StringBuilder sb = new StringBuilder();
        sb.append("Slot No.\t Registration No \t Colour\n");
        String cars = occupiedSlots.values().stream()
                .map(ps -> {
                    Car c = ps.getCar();
                    return String.format("%s\t%s\t%s", ps.getSpotNumber(), c.getRegistrationNumber(), c.getColor());
                })
                .collect(Collectors.joining("\n"));
        sb.append(cars);
        return sb.toString();
    }

    @Override
    public String toString() {
        return "ParkingLotImpl{" +
                "capacity=" + capacity +
                ", occupiedSlots=" + occupiedSlots +
                ", occupiedSlotsByRegNumber=" + occupiedSlotsByRegNumber +
                ", availableSlots=" + availableSlots +
                '}';
    }
}

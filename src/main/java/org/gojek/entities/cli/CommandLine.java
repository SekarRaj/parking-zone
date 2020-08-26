package org.gojek.entities.cli;

import org.gojek.entities.domain.Car;
import org.gojek.entities.domain.ParkingLot;
import org.gojek.entities.domain.ParkingLotImpl;
import org.gojek.entities.domain.ParkingZone;
import org.gojek.entities.exception.InvalidCommandParameterException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class CommandLine {
    static ParkingLot lot;

    public static void main(String[] args) throws IOException {
        ParkingZone zone = new ParkingZone();
        System.out.println("Enter command w/ arguments");
        boolean loop = true;
        while (loop) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String command = reader.readLine();

            String[] atoms = command.split(" ");

            try {
                switch (atoms[0]) {
                    case "create_parking_lot":
                        if (!Objects.isNull(lot))
                            throw new IllegalStateException("Parking lot has been created already");

                        try {
                            int capacity = Integer.parseInt(atoms[1]);
                            lot = ParkingLotImpl.withCapacity(capacity);
                            zone.addLot(lot);
                        } catch (Exception ex) {
                            throw new InvalidCommandParameterException("Create lot requires lot capacity as parameter");
                        }
                        break;
                    case "park":
                        if (Objects.isNull(lot))
                            throw new IllegalStateException("Parking lot must be created before parking");

                        if (atoms.length < 3) {
                            throw new InvalidCommandParameterException("Parking car should have valid registration number and color as parameter");
                        }
                        System.out.println(lot.parkCar(Car.withRegistrationNumberAndColor(atoms[1], atoms[2])));
                        break;
                    case "leave":
                        if (Objects.isNull(lot))
                            throw new IllegalStateException("Parking lot must be created before parking");

                        if (atoms.length < 2) {
                            throw new InvalidCommandParameterException("Leaving car slot number is a required parameter");
                        }

                        lot.leaveParking(Integer.parseInt(atoms[1]));
                        break;
                    case "status":
                        if (Objects.isNull(lot))
                            throw new IllegalStateException("Parking lot must be created before parking");

                        System.out.println(lot.status());
                        break;
                    case "registration_numbers_for_cars_with_colour":
                        if (Objects.isNull(lot))
                            throw new IllegalStateException("Parking lot must be created before parking");

                        if (atoms.length < 2) {
                            throw new InvalidCommandParameterException("Color of the car is a required parameter");
                        }

                        List<String> cars = lot.getRegNumberOfCarsWithColor(atoms[1]);
                        System.out.println(cars);
                        break;
                    case "slot_numbers_for_cars_with_colour":
                        if (Objects.isNull(lot))
                            throw new IllegalStateException("Parking lot must be created before parking");

                        if (atoms.length < 2) {
                            throw new InvalidCommandParameterException("Color of the car is a required parameter");
                        }

                        List<Integer> slots = lot.getSlotNumberOfCarWithColor(atoms[1]);
                        System.out.println(slots);
                        break;
                    case "slot_number_for_registration_number":
                        if (Objects.isNull(lot))
                            throw new IllegalStateException("Parking lot must be created before parking");

                        if (atoms.length < 2) {
                            throw new InvalidCommandParameterException("Color of the car is a required parameter");
                        }

                        int slot = lot.getSlotNumberOfCarWithRegistrationNumber(Car.withRegistrationNumber(atoms[1]));
                        System.out.println(slot);
                        break;
                    case "dispatch_rule":
                        if (atoms.length < 2) {
                            throw new InvalidCommandParameterException("Color of the car is a required parameter");
                        }

                        zone.changeDistributionStrategy(atoms[1]);
                    case "exit":
                        loop = false;
                        break;
                    default:
                        System.out.println("Please enter a valid command");
                }
            } catch (InvalidCommandParameterException | IllegalStateException ex) {
                System.err.println(ex.getLocalizedMessage());
            }
        }
    }
}

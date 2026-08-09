package com;
import java.util.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        interface Rentable {
            double calculateRentalCost(int days);

            boolean isAvailableForRent();
        }
        abstract class Vehicle implements Rentable {
            // ENCAPSULATION
            private final String plateNumber;
            private final String model;
            private boolean rented;

            public Vehicle(String plateNumber, String model) {
                this.plateNumber = plateNumber;
                this.model = model;
                this.rented = false;
            }
            public String getPlateNumber() { return plateNumber; }
            public String getModel() { return model; }
            public boolean isRented() { return rented; }

            public void markRented() { this.rented = true; }
            public void markReturned() { this.rented = false; }

            @Override
            public boolean isAvailableForRent() {
                return !rented;
            }
            public abstract String displayDetails();
            public abstract double lateFeePerDay();
        }
        class Car extends Vehicle {
            private static final double DAILY_RATE = 45.0;
            private int seats;

            public Car(String plateNumber, String model, int seats) {
                super(plateNumber, model);
                this.seats = seats;
            }

            @Override
            public double calculateRentalCost(int days) {
                double cost = days * DAILY_RATE;
                if (days >= 7) cost *= 0.9; // 10% weekly discount
                return cost;
            }

            @Override
            public double lateFeePerDay() {
                return 20.0;
            }

            @Override
            public String displayDetails() {
                return String.format("Car [%s] %s - %d seats - $%.2f/day", getPlateNumber(), getModel(), seats, DAILY_RATE);
            }
        }

        class Bike extends Vehicle {
            private static final double DAILY_RATE = 15.0;
            private String type; // e.g., "Mountain", "Road"

            public Bike(String plateNumber, String model, String type) {
                super(plateNumber, model);
                this.type = type;
            }

            @Override
            public double calculateRentalCost(int days) {
                return days * DAILY_RATE;
            }

            @Override
            public double lateFeePerDay() {
                return 5.0;
            }

            @Override
            public String displayDetails() {
                return String.format("Bike [%s] %s - %s type - $%.2f/day", getPlateNumber(), getModel(), type, DAILY_RATE);
            }
        }

        class Truck extends Vehicle {
            private static final double DAILY_RATE = 90.0;
            private double capacityTons;

            public Truck(String plateNumber, String model, double capacityTons) {
                super(plateNumber, model);
                this.capacityTons = capacityTons;
            }

            @Override
            public double calculateRentalCost(int days) {
                double cost = days * DAILY_RATE;
                if (days >= 5) cost *= 0.85; // 15% bulk discount
                return cost;
            }

            @Override
            public double lateFeePerDay() {
                return 50.0;
            }

            @Override
            public String displayDetails() {
                return String.format("Truck [%s] %s - %.1f ton capacity - $%.2f/day", getPlateNumber(), getModel(), capacityTons, DAILY_RATE);
            }
        }
        class RentalService {
            private List<Vehicle> fleet = new ArrayList<>();

            public void addVehicle(Vehicle v) {
                fleet.add(v);
            }

            public void showFleet() {
                System.out.println("=== Fleet ===");
                for (Vehicle v : fleet) {
                    // POLYMORPHISM: displayDetails() call resolves to the correct subclass method
                    System.out.println(v.displayDetails() + " | " + (v.isAvailableForRent() ? "Available" : "Rented"));
                }
            }

            public void rentVehicle(String plateNumber, int days) {
                Vehicle v = findVehicle(plateNumber);
                if (v == null) {
                    System.out.println("Vehicle not found: " + plateNumber);
                    return;
                }
                if (!v.isAvailableForRent()) {
                    System.out.println(v.getModel() + " (" + plateNumber + ") is already rented.");
                    return;
                }
                double cost = v.calculateRentalCost(days); // POLYMORPHIC dispatch
                v.markRented();
                System.out.printf("Rented %s for %d days. Total cost: $%.2f%n", v.getModel(), days, cost);
            }

            public void returnVehicle(String plateNumber, int daysLate) {
                Vehicle v = findVehicle(plateNumber);
                if (v == null || !v.isRented()) {
                    System.out.println("Cannot return - vehicle not currently rented: " + plateNumber);
                    return;
                }
                v.markReturned();
                if (daysLate > 0) {
                    double fee = daysLate * v.lateFeePerDay(); // POLYMORPHIC dispatch
                    System.out.printf("Returned %s late by %d days. Late fee: $%.2f%n", v.getModel(), daysLate, fee);
                } else {
                    System.out.println("Returned " + v.getModel() + " on time. No late fee.");
                }
            }

            private Vehicle findVehicle(String plateNumber) {
                for (Vehicle v : fleet) {
                    if (v.getPlateNumber().equals(plateNumber)) return v;
                }
                return null;
            }
        }

        public class VehicleRentalSystem {
            public static void main(String[] args) {
                RentalService service = new RentalService();

                service.addVehicle(new Car("CAR-101", "Toyota Corolla", 5));
                service.addVehicle(new Bike("BIKE-201", "Trek Marlin", "Mountain"));
                service.addVehicle(new Truck("TRK-301", "Ford F-150", 2.5));

                service.showFleet();

                System.out.println("\n--- Renting Vehicles ---");
                service.rentVehicle("CAR-101", 8);   // qualifies for weekly discount
                service.rentVehicle("BIKE-201", 3);
                service.rentVehicle("TRK-301", 6);   // qualifies for bulk discount
                service.rentVehicle("CAR-101", 2);   // already rented -> denied

                System.out.println();
                service.showFleet();

                System.out.println("\n--- Returning Vehicles ---");
                service.returnVehicle("CAR-101", 2);  // late fee applies
                service.returnVehicle("BIKE-201", 0); // on time
                service.returnVehicle("TRK-301", 1);  // late fee applies

                System.out.println();
                service.showFleet();
            }
        }


    }
    }

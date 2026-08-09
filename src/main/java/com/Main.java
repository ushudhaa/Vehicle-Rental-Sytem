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

        }
    }

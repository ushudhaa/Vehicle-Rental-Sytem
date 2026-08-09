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

        }
    }

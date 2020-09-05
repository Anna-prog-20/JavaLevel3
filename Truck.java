package com.company;

import java.util.concurrent.Semaphore;

public class Truck extends Transport {
    public Truck(float volume, float consumption, String name, Semaphore semaphore, FuelStation fuelStation) {
        super(volume, consumption, name, semaphore, fuelStation);
    }
}
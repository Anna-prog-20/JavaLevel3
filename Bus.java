package com.company;

import java.util.concurrent.Semaphore;

public class Bus extends Transport{
    public Bus(float volume, float consumption, String name, Semaphore semaphore, FuelStation fuelStation) {
        super(volume, consumption, name, semaphore, fuelStation);
    }
}

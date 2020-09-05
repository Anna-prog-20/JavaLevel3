package com.company;

import java.util.concurrent.Semaphore;

public class Car extends Transport{

    public Car(float volume, float consumption, String name, Semaphore semaphore, FuelStation fuelStation) {
        super(volume, consumption, name, semaphore, fuelStation);
    }
}

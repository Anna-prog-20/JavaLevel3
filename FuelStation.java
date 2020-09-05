package com.company;

import java.util.concurrent.Callable;

public class FuelStation implements Callable<Float> {

    public float refuel() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 15f;
    }

    @Override
    public Float call() throws Exception {
        return refuel();
    }

}

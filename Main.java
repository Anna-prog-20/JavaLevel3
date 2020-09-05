package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(3);
        ExecutorService executorService = Executors.newFixedThreadPool(11);
        FuelStation fuelStation=new FuelStation();
        executorService.submit(fuelStation);
        executorService.submit(new Car(20f,2.5f,"жигули",semaphore,fuelStation));
        executorService.submit(new Truck(60f,15f,"краз",semaphore,fuelStation));
        executorService.submit(new Bus(40f,7.5f,"ЛиАЗ",semaphore,fuelStation));
        executorService.submit(new Car(20f,2.5f,"ларгус",semaphore,fuelStation));
        executorService.submit(new Bus(40f,7.5f,"ПАЗ",semaphore,fuelStation));
        executorService.submit(new Car(20f,2.5f,"калина",semaphore,fuelStation));
        executorService.submit(new Car(20f,2.5f,"УАЗ",semaphore,fuelStation));
        executorService.submit(new Car(20f,2.5f,"нива",semaphore,fuelStation));
        executorService.submit(new Car(20f,2.5f,"веста",semaphore,fuelStation));
        executorService.submit(new Truck(60f,15f,"камаз",semaphore,fuelStation));
        executorService.submit(new Car(20f,2.5f,"волга",semaphore,fuelStation));
        executorService.shutdown();
    }
}

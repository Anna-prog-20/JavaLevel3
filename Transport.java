package com.company;

import java.util.concurrent.Semaphore;

public class Transport implements Runnable{
    private float volume;
    private float consumption;
    private String name;
    private Semaphore semaphore;
    private float[] remainingConsumption;
    private FuelStation fuelStation;

    public Transport(float volume, float consumption, String name, Semaphore semaphore, FuelStation fuelStation) {
        this.volume = volume;
        this.consumption = consumption;
        this.name = name;
        this.semaphore = semaphore;
        this.fuelStation = fuelStation;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }


    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getConsumption() {
        return consumption;
    }

    public void setConsumption(float consumption) {
        this.consumption = consumption;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float[] getRemainingConsumption(){
        remainingConsumption = new float[]{getVolume(),1f};
        try {
            while (remainingConsumption[0] - getConsumption() >= 0){
                Thread.sleep(3000);
                remainingConsumption[0] -= getConsumption();
                remainingConsumption[1]=1f;
            }
            if(remainingConsumption[0] - getConsumption() <= 0) {
                System.out.println(String.format("%S - остаток топлива %f - нужна ЗАПРАВКА!!!", getName(), remainingConsumption[0]));
                remainingConsumption[1] = 0f;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    return remainingConsumption;
    }

    private void go(){
        System.out.println(String.format("%S едет, в баке %f", getName(), getVolume()));
    }

    private void refuel(){
        if(getRemainingConsumption()[1]==0f){
            try {
                System.out.println(String.format("%S стою в очереди на заправку",getName()));
                semaphore.acquire();
                System.out.println(String.format("%S начал заправляться",getName()));
                setVolume(fuelStation.refuel()+remainingConsumption[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                System.out.println(String.format("%S заправился и поехал дальше, в баке %f",getName(), getVolume()));
                semaphore.release();
                run();
            }
        }
    }

    @Override
    public void run() {
        go();
        refuel();
    }
}

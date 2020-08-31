package com;

public class Lesson4Item1 {
    private final Object mon = new Object();
    private volatile char currentLetter = 'A';

    public static void main(String[] args) {
        Lesson4Item1 w = new Lesson4Item1();
        Thread t1 = new Thread(() -> {
            w.printA();
        });
        Thread t2 = new Thread(() -> {
            w.printB();
        });
        Thread t3 = new Thread(() -> {
            w.printC();
        });
        t1.start();
        t2.start();
        t3.start();
    }

    public void printA() {
        synchronized (mon) {
            try {
                print('A','B',mon);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB() {
        synchronized (mon) {
            try {
                print('B','C',mon);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printC() {
        synchronized (mon) {
            try {
                print('C','A',mon);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void print(char symbol, char symbolNext,Object mon) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            while (currentLetter != symbol) {
                mon.wait();
            }
            System.out.print(symbol);
            currentLetter = symbolNext;
            mon.notifyAll();
        }
    }
}
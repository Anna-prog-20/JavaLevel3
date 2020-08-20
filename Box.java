package com.company;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    private ArrayList<T> fruit=new ArrayList<T>();
    private double weight;


    public ArrayList<T> getFruit() {
        return fruit;
    }


    public double getWeight(){
        double weightOneFruit=fruit.get(0).getWeight();
        weight=fruit.size()*weightOneFruit;
        return weight;
    }

    public double count(){
        return fruit.size();
    }

    public boolean compare(Box box){
        return weight == box.getWeight();
    }

    public void addFruit(T addF){
        fruit.add(addF);
    }

    public void putFruit(Box<T> box){
        for(int i=0;i<fruit.size();i++){
            box.addFruit(fruit.get(i));
        }
        for(int i=0;i<fruit.size();i++){
            fruit.remove(i);
        }

    }

    public void print(){
        System.out.print("Коробка:");
        System.out.println();
        System.out.print(fruit);
    }
}

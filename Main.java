package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        String[] s=new String[]{"1", "2", "5", "6"};
        //Написать метод, который меняет два элемента массива местами.
        // (массив может быть любого ссылочного типа);
	    swapElements(s,1,3);
	    //Написать метод, который преобразует массив в ArrayList;
        convertArray(s);
        //Большая задача :))))
        putBox();
    }

    //Написать метод, который меняет два элемента массива местами.
    // (массив может быть любого ссылочного типа);
    static void swapElements(Object[] array,int i,int j){
        Object aI,aJ;
        if (i<0|i>=array.length|j<0|j>=array.length){
            System.out.print("Индексы переносимых элементов вышли за границы!");
        }else{
            aI=array[i];
            aJ=array[j];
            array[i]=aJ;
            array[j]=aI;
            System.out.print(Arrays.toString(array));
        }
        System.out.println();
    }

    //Написать метод, который преобразует массив в ArrayList;
    static void convertArray(Object[] array){
        ArrayList arrayList=new ArrayList();
        for(Object a:array)
            arrayList.add(a);
        System.out.print(arrayList);
        System.out.println();
    }

    static void putBox(){
        Box<Apple> appleBox=new Box<>();
        for(int i=0;i<5;i++)
            appleBox.addFruit(new Apple(1.0f));
        System.out.println("Вес 1ой коробки ["+appleBox.count()+"шт.] с яблоками "+appleBox.getWeight());

        Box<Apple> appleBox1=new Box<>();
        for(int i=0;i<10;i++)
            appleBox1.addFruit(new Apple(1.0f));
        System.out.println("Вес 2ой коробки ["+appleBox1.count()+"шт.] с яблоками "+appleBox1.getWeight());

        Box<Orange> orangeBox=new Box<>();
        for(int i=0;i<6;i++)
            orangeBox.addFruit(new Orange(1.5f));
        System.out.println("Вес коробки ["+orangeBox.count()+"шт.] с апельсинами "+orangeBox.getWeight());

        System.out.println("Сравнение 1ой коробки яблок с коробкой апельсин "+appleBox.compare(orangeBox));

        appleBox.putFruit(appleBox1);
        System.out.println("Новый вес коробки с яблоками "+appleBox1.getWeight());


    }
}

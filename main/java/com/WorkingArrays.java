package com;

import java.util.Arrays;

public class WorkingArrays {

    public int[] getNewArray(int[] arrayInt){
        int[] newArray;
        int j=-1;
        for (int i=0;i<arrayInt.length;i++) {
            if(arrayInt[i]==4){
                j=i+1;
            }
        }
        if(j!=-1){
            newArray=new int[arrayInt.length-j];
            for (int i = j; i <arrayInt.length; i++) {
                newArray[i-j]=arrayInt[i];
            }
            return newArray;
        }else {
            throw new NoNumber("В данном массиве не встречается цифра 4");
        }
    }

    public boolean checkNumber(int[] arrayInt){
        int n1=0;
        int n4=0;
        for (int i=0;i<arrayInt.length;i++) {
            if(arrayInt[i]==1){
                n1=1;
            }
            if(arrayInt[i]==4){
                n4=1;
            }
        }
        if(n1==1&n4==1)
            return true;
        else
            return false;
    }

    public void printArray(int[] array){
        System.out.print(Arrays.toString(array));
        System.out.println();
    }

    public void printNewArray(int[] array){
        System.out.print(Arrays.toString(getNewArray(array)));
        System.out.println();
    }

    public void printCheckNumber(int[] array){
        System.out.print("Присутствие 1 и 4 в массиве - "+String.valueOf(checkNumber(array)));
        System.out.println();
    }
}

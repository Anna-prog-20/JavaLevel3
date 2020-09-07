package com;

public class Main {

    public static void main(String[] args) {
	    WorkingArrays workingArrays=new WorkingArrays();
	    int[] array=new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7};
	    workingArrays.printArray(array);
	    workingArrays.printNewArray(workingArrays.getNewArray(array));
	    workingArrays.printCheckNumber(array);
    }
}

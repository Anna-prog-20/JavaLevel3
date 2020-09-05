package com;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.*;

public class WorkingArraysTest {
    private WorkingArrays workingArrays;
    private int[][] arrays;

    @BeforeEach
    void setUp() {
        workingArrays = new WorkingArrays();
        arrays=new int[][]{{1, 2, 4, 4, 2, 3, 4, 1, 7},{1, 1, 2, 10, 3, 4, 4, 4, 7},{1, 2, 6, 6, 2, 3, 6, 1, 4},{1, 2, 3, 3, 1, 7}};
    }

    @Test
    @NullSource
    void shouldGetNewArray() {
        int i=1;
        for (int[] array: arrays) {
            System.out.println("*********Тест №"+i);
            workingArrays.printArray(array);
            workingArrays.printNewArray(array);
            i++;
        }

    }

    @Test
    @NullSource
    void shouldCheckNumber() {
        int i=1;
        for (int[] array: arrays) {
            System.out.println("*********Тест №"+i);
            workingArrays.printArray(array);
            workingArrays.printCheckNumber(array);
            i++;
        }
    }
}

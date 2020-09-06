package com;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class WorkingArraysTest {
    private WorkingArrays workingArrays;
    private int[][] arrays;

    @BeforeEach
    void setUp() {
        workingArrays = new WorkingArrays();
        arrays=new int[][]{{1, 2, 4, 4, 2, 3, 4, 1, 7},{1, 1, 2, 10, 3, 4, 4, 4, 7},{1, 2, 6, 6, 2, 3, 6, 1, 4}};
    }

    @Test
    void shouldGetNewArray() {
        int i=1;
        for (int[] array: arrays) {
            System.out.println("*********Тест №"+i);
            Assertions.assertNotNull(workingArrays.getNewArray(array), "Не может вернуться NULL");
            i++;
        }

    }

    @Test
    void shouldGetNewArrayNoNumber(){
        Assertions.assertThrows(NoNumber.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                workingArrays.getNewArray(new int[]{1, 2, 3, 3, 1, 7});
            }
        });
    }

    @Test
    void shouldCheckNumber() {
        int i=1;
        for (int[] array: arrays) {
            System.out.println("*********Тест №"+i);
            Assertions.assertNotNull(workingArrays.checkNumber(array), "Не может вернуться NULL");
            i++;
        }
    }
}

package com.company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.annotations.AfterSuite;

public class ClassTest {
    @PriorityTest
    private int priority;
    private String nameTest;

    public ClassTest(int priority, String nameTest) {
        this.priority = priority;
        this.nameTest = nameTest;
    }

    public int getPriority() {
        return priority;
    }
    public void print(){
        System.out.println(nameTest);
    }

    @Test
    public void test1(){
        System.out.println("Запустился тест 1");
    }

    @Test
    public void test2(){
        System.out.println("Запустился тест 2");
    }

    @Test
    public void test3(){
        System.out.println("Запустился тест 3");
    }

    @Test
    public void test4(){
        System.out.println("Запустился тест 4");
    }

    @BeforeEach
    public void beforeEach(){
        System.out.println("Запустился тест beforeEach");
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("Запустился тест afterSuite");
    }
}

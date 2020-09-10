package com.company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.annotations.AfterSuite;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;

public class ExecutionTest {
    private static ArrayList<ClassTest> tests;

    public static void main(String[] args) {
        ExecutionTest executionTest=new ExecutionTest();
//        tests=new ArrayList<>();
//        tests.add(new ClassTest(5, "******* Перый тест *******"));
//        tests.add(new ClassTest(10, "******* Второй тест *******"));
//        tests.add(new ClassTest(10, "******* Третий тест *******"));
//        tests.add(new ClassTest(6, "******* Четвертый тест *******"));
//        tests.add(new ClassTest(3, "******* Пятый тест *******"));
//
//        tests.sort(Comparator.comparing(ClassTest::getPriority).reversed());
//        for (int i=0;i<tests.size();i++) {
//            tests.get(i).print();
//            start(tests.get(i).getClass());
//        }
//        tests.clear();

        tests=new ArrayList<>();
        tests.add(new ClassTest(5, "******* Перый тест *******"));
        tests.add(new ClassTest(10, "******* Второй тест *******"));
        tests.add(new ClassTest(10, "******* Третий тест *******"));
        tests.add(new ClassTest(6, "******* Четвертый тест *******"));
        tests.add(new ClassTest(3, "******* Пятый тест *******"));

        tests.sort(Comparator.comparing(ClassTest::getPriority).reversed());
        for (int i=0;i<tests.size();i++) {
            tests.get(i).print();
            start(tests.get(i).getClass().getName());
        }
    }

    static void start(Class aClass){
        try {
            executionMethod(aClass.getMethod("beforeEach"),aClass,BeforeEach.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        for (Method method : aClass.getMethods()) {
            executionMethod(method,aClass,Test.class);
        }
        try {
            executionMethod(aClass.getMethod("afterSuite"),aClass,AfterSuite.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static void executionMethod(Method method,Class aClass,Class annotation){
        if (method.isAnnotationPresent(annotation))
        {
            try {
                aClass.getMethod(method.getName());
                System.out.println("Запустился "+method.getName());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    static void start(String nameClass){
        if(nameClass.getClass()!=null){
            try {
                start(Class.forName(nameClass));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}

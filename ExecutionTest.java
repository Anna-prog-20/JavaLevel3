package com.company;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class ExecutionTest {
    private static ArrayList<ClassTest> tests;

    public static void main(String[] args) {
        tests=new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            tests.add(addClassTest(i));
        }

        tests.sort(Comparator.comparing(ClassTest::getPriority).reversed());

        for (int i=0;i<tests.size();i++) {
            tests.get(i).print();
            start(tests.get(i).getClass(),tests.get(i));
        }

        System.out.println("--------------");
        for (int i=0;i<tests.size();i++) {
            tests.get(i).print();
            start(tests.get(i).getClass().getName(),tests.get(i));
        }
    }

    static void start(Class aClass,ClassTest classTest){
        try {
            executionMethod(aClass.getMethod("beforeSuite"),aClass,BeforeSuite.class,classTest);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        for (Method method : aClass.getMethods()) {
            executionMethod(method,aClass,Test.class,classTest);
        }
        try {
            executionMethod(aClass.getMethod("afterSuite"),aClass, AfterSuite.class,classTest);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    static void start(String nameClass,ClassTest classTest){
        if(nameClass.getClass()!=null){
            try {
                start(Class.forName(nameClass),classTest);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    private static void executionMethod(Method method,Class aClass,Class annotation,ClassTest classTest){
        if (method.isAnnotationPresent(annotation))
        {
            try {
                aClass.getMethod(method.getName()).invoke(classTest);
                //System.out.println("Запустился "+method.getName());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static ClassTest addClassTest(int numberTest) {
        ClassTest classTest = new ClassTest();
        classTest.ClassTest();
        Random randomPriority=new Random();
        for (Method method : classTest.getClass().getMethods()) {
            if (method.isAnnotationPresent(ParametersTest.class)) {
                try {
                    classTest.getClass().getMethod(method.getName());
                    ParametersTest anno = method.getAnnotation(ParametersTest.class);
                    if (numberTest==1){
                        writeData(classTest,anno.priority(), anno.nameTest());;
                    }
                    else {
                        writeData(classTest, randomPriority.nextInt(9)+1, String.format("******* Тест №%d *******",numberTest));
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        return classTest;
    }

    private static void writeData(ClassTest classTest,int priority, String nameTest){
        for (Field field : classTest.getClass().getDeclaredFields()) {
            try {
                if (field.getName().equals("priority")) {
                    setData(field,classTest,priority);
                } else if (field.getName().equals("nameTest")) {
                    setData(field,classTest,nameTest);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void setData(Field field,ClassTest classTest,Object parameter) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(classTest, parameter);
        field.setAccessible(false);
    }

}

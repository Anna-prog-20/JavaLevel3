package com.company;

public class ClassTest {
    private int priority;
    private String nameTest;

    public ClassTest(int priority, String nameTest) {
        this.priority = priority;
        this.nameTest = nameTest;
    }

    public ClassTest() {
    }

    @ParametersTest(priority = 5,nameTest = "******* Тест №1 *******")
    public ClassTest ClassTest(){
        return new ClassTest(priority,nameTest);
    }

    public int getPriority() {
        return priority;
    }

    public void print(){
        System.out.println(nameTest);
    }

    public void printTest(String s){
        System.out.println(String.format("Запустился %s",s));
    }

    @Test
    public void test1(){ printTest("test1"); }

    @Test
    public void test2(){
        printTest("test2");
    }

    @Test
    public void test3(){
        printTest("test3");
    }

    @Test
    public void test4(){
        printTest("test4");
    }

    @BeforeSuite
    public void beforeSuite(){
        printTest("beforeSuite");
    }

    @AfterSuite
    public void afterSuite(){
        printTest("afterSuite");
    }
}

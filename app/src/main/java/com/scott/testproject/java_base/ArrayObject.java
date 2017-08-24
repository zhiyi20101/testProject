package com.scott.testproject.java_base;


import java.util.Arrays;

/**
 * Created by zouzhiyi on 21/07/17.
 */

public class ArrayObject {
    @org.junit.Test
    public void test(){
        Object[] a = new Object[5];
        System.out.print(a[2]);

    }

    public synchronized static void method1(){
        try {
            Thread.sleep(3000);
            System.out.println("method1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void method2(){
        System.out.println("method2");
    }
}

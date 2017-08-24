package com.scott.testproject.java_base;

import org.junit.Test;

import java.util.Calendar;

/**
 * Created by zouzhiyi on 07/08/17.
 */

public class StringReverse {
    @Test
    public void test(){
        //System.out.println(reverse("love"));
        //System.out.println(f(5));
        //System.out.println(testCalander());
        final String lock = "lock";
        new Thread(new Runnable() {
            @Override
            public void run() {
//                System.out.println("11111");
//                synchronized (lock){
//                    try {
//                        System.out.println("222222");
//                        lock.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                System.out.println("33333333");
                ArrayObject.method1();
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
//                System.out.println("4444444");
//                synchronized (lock){
//                    try {
//                        System.out.println("5555555");
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                System.out.println("666666666");
//                lock.notify();
                ArrayObject arrayObject = new ArrayObject();
                arrayObject.method2();
            }
        }).start();
    }

    //递归方式实现字符串反转
    public static String reverse(String oriString){
        System.out.println("1-"+oriString);
        if (oriString == null || oriString.length() <= 1){
            System.out.println("2-"+oriString);
            return oriString;
        }
        return reverse(oriString.substring(1)) + oriString.charAt(0);
    }

    public static int f(int n) {
        if (1 == n)
            return 1;
        else
            return n*f(n-1);
    }


    public int testCalander(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-2);
        System.out.println("date:"+calendar.getTime());
        int g = 100;
        try {
            System.out.println("1111111");
            return g;
        }finally {
            g = 101;
            System.out.println("222222");
        }

    }


}

package com.scott.testproject.java_base;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by zouzhiyi on 09/08/17.
 */

public class ProducerConsumerInJava {
    @Test
    public void test() {
        System.out.println("How to use wait and notify method in Java");
        System.out.println("Solving Producer Consumper Problem");
        Queue buffer = new LinkedList();
        int maxSize = 10;
        Thread producer = new Producer("PRODUCER", buffer, maxSize);
        Thread consumer = new Consumer("CONSUMER", buffer, maxSize);
        producer.start();
        consumer.start();
    }

    class Producer extends Thread {
        private Queue mQueue;
        private int mMaxSize;

        public Producer(String name, Queue queue, int maxSize) {
            super(name);
            mQueue = queue;
            mMaxSize = maxSize;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (mQueue) {
                    while (mQueue.size() == mMaxSize) {
                        try {
                            System.out.println("Queue is full, " + "Producer thread waiting for "
                                    + "consumer to take something from queue");
                            mQueue.wait();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    Random random = new Random();
                    int i = random.nextInt();
                    System.out.println("Producing value : " + i);
                    mQueue.add(i);
                    mQueue.notifyAll();
                }
            }
        }
    }

    class Consumer extends Thread {
        private Queue mQueue;
        private int mMaxSize;

        public Consumer(String name, Queue queue, int maxSize) {
            super(name);
            mQueue = queue;
            mMaxSize = maxSize;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (mQueue) {
                    while (mQueue.isEmpty()) {
                        System.out.println("Queue is empty," + "Consumer thread is waiting"
                                + " for producer thread to put something in queue");
                        try {
                            mQueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Consuming value : " + mQueue.remove());
                    mQueue.notifyAll();
                }
            }
        }
    }

}

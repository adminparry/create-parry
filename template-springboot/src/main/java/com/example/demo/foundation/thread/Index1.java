package com.example.demo.foundation.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class Index1 {


    private static final AtomicInteger atomicInteger = new AtomicInteger(0);
    private static volatile boolean flag = true;

    public static void main(String[] args) {


        new Thread(() -> {
            while (atomicInteger.get() < 201){
                if(flag){

                    System.out.println(Thread.currentThread().getName() + ":" + atomicInteger.getAndIncrement());
                    flag = false;
                }
            }
        }, "A_Thread").start();

        new Thread(() -> {
            while (atomicInteger.get() < 201){
                if(!flag){

                    System.out.println(Thread.currentThread().getName() + ":" + atomicInteger.getAndIncrement());
                    flag = true;
                }
            }
        }, "B_Thread").start();
    }
}

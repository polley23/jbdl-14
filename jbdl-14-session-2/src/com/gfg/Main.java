package com.gfg;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        Runnable runnable = new RunnableImpl();
//        Thread thread1 = new Thread(runnable);
//        Runnable runnable2 = new Runnable(){
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName() +  " Happy Coding");
//            }
//        };
//        Thread thread2 = new Thread(runnable);
//        thread1.setPriority(10);
//        thread2.setPriority(1);
//        thread1.start();
//        thread2.start();
//        OddEvenPrinter oddEvenPrinter = new OddEvenPrinter(100000);
//        Runnable runnableOdd = new Runnable() {
//            @Override
//            public void run() {
//                oddEvenPrinter.printOdd();
//            }
//        };
//        Runnable runnableEven = new Runnable() {
//            @Override
//            public void run() {
//                oddEvenPrinter.printEven();
//            }
//        };
//        Thread thread1 = new Thread(runnableOdd , "Odd-Thread");
//        Thread thread2 = new Thread(runnableEven , "Even-Thread");
//
//        thread1.start();
//        thread2.start();

        ConcurrentLinkedQueue<Integer> list = new ConcurrentLinkedQueue<>();

        list.add(1);list.add(2);list.add(3);list.add(4);

        for(Integer i : list){
            System.out.println(i);
            list.remove(i);
        }

    }

    /*
    We will have 2 threads. One thread prints when the value of the shared resource is odd
    Other thread prints when value of the shared resource is even
    1,2,3,4,5.....
     */
}

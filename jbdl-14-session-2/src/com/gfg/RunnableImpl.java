package com.gfg;

public class RunnableImpl  implements Runnable{

    @Override
    public void run() {
        for(int i = 0 ;i<100000000; i++){

            System.out.println(Thread.currentThread().getName() + " value : "+i);
        }
    }
}

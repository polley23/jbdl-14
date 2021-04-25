package com.gfg;

public class OddEvenPrinter {
    private Integer count = 0 ;
    private Integer maxCount;

    public OddEvenPrinter(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public void printOdd(){
        //100 line
        while(count<maxCount){
            synchronized (this) {
                if (count % 2 == 1) {
                    System.out.println(Thread.currentThread().getName()
                            + " : " + count);
                    count++;
                    this.notify();
                }else{
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public  void printEven(){
        while(count<maxCount){
            synchronized (this) {
                if (count % 2 == 0) {
                    System.out.println(Thread.currentThread().getName()
                            + " : " + count);
                    count++;
                    this.notify();
                }else{
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

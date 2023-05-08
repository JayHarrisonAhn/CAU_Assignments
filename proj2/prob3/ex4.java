package prob3;

import java.util.concurrent.CyclicBarrier;

public class ex4 {
    static int numOfThreads = 5;

    public static void main(String[] args) {
        WaitingThread[] threads = new WaitingThread[numOfThreads];
        CyclicBarrier barrier = new CyclicBarrier(numOfThreads);
        long startTime = System.currentTimeMillis();
        for(int i=0; i<numOfThreads; i++) threads[i] = new WaitingThread(i, startTime, barrier);
    }
}

class WaitingThread extends Thread {
    int threadNum;
    long startTime;
    CyclicBarrier barrier;
    WaitingThread(int threadNum, long startTime, CyclicBarrier barrier) {
        this.threadNum = threadNum;
        this.startTime = startTime;
        this.barrier = barrier;
        this.start();
    }

    @Override
    public void run() {
        try {
            long diff;
            diff = System.currentTimeMillis() - startTime;
            System.out.println(diff + " Thread " + threadNum + " start sleeping");
            sleep((int)(Math.random() * 10000));
            diff = System.currentTimeMillis() - startTime;
            System.out.println(diff + " Thread " + threadNum + " finished sleeping & waiting");
            barrier.await();
            diff = System.currentTimeMillis() - startTime;
            System.out.println(diff + " Thread " + threadNum + " finished waiting");
        } catch (Exception e) {}
    }
}
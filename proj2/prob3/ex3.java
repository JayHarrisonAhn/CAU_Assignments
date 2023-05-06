package prob3;

import java.util.concurrent.atomic.AtomicInteger;

public class ex3 {
    static int numOfThreads = 3;

    public static void main(String[] args) {
        AtomicInteger variable = new AtomicInteger(0);

        NumThread[] threads = new NumThread[numOfThreads];
        for(int i=0; i<numOfThreads; i++) threads[i] = new NumThread(i, variable);
    }
}

class NumThread extends Thread {
    AtomicInteger variable;
    int threadNumber;
    NumThread(int threadNumber, AtomicInteger variable) {
        this.threadNumber = threadNumber;
        this.variable = variable;
        this.start();
    }

    @Override
    public void run() {
        while(true) {
            try {
                sleep(10);
                double rand = Math.random();
                if (rand < 0.25) {
                    System.out.println("Thread " + threadNumber + " read " + variable.get());
                } else if(rand < 0.5) {
                    System.out.println("Thread " + threadNumber + " read and add 3 " + variable.getAndAdd(3));
                } else if(rand < 0.75) {
                    System.out.println("Thread " + threadNumber + " add 3 and read " + variable.addAndGet(3));
                } else {
                    System.out.println("Thread " + threadNumber + " trying to write");
                    int newVariable = (int) (Math.random() * 100) + 1;
                    variable.set(newVariable);
                    System.out.println("Thread " + threadNumber + " wrote " + newVariable);
                }
            } catch (InterruptedException e) {
            }
        }
    }
}
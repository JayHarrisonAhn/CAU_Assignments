package prob3;
import java.util.concurrent.ArrayBlockingQueue;

public class ex1 {
    static ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);

    public static void main(String[] args) {
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
    }
}

class Producer extends Thread {
    ArrayBlockingQueue<Integer> queue;
    Producer(ArrayBlockingQueue<Integer> queue) {
        this.queue = queue;
        this.start();
    }

    @Override
    public void run() {
        while(true) {
            try {
                sleep((int)(Math.random() * 500)); // stay within the parking garage
                System.out.println("Producer trying to put");
                queue.put(0);
                System.out.println("Producer put");
            } catch (InterruptedException e) {}
        }
    }
}

class Consumer extends Thread {
    ArrayBlockingQueue<Integer> queue;
    Consumer(ArrayBlockingQueue<Integer> queue) {
        this.queue = queue;
        this.start();
    }

    @Override
    public void run() {
        while(true) {
            try {
                sleep((int)(Math.random() * 1000)); // stay within the parking garage
                System.out.println("Consumer trying to take");
                queue.take();
                System.out.println("Consumer took");
            } catch (InterruptedException e) {}
        }
    }
}
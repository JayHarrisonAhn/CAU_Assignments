package problem1;

import javax.swing.text.html.Option;
import java.util.OptionalInt;

public class pc_dynamic {
    private static final int NUM_START = 0;
    private static int NUM_END = 200000;
    private static int NUM_THREADS = 4;
    private static final int SIZE_OF_TASK = 10;

    public static void main (String[] args) {
        if (args.length == 2) {
            NUM_THREADS = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }
        pc_dynamic_counter counter = new pc_dynamic_counter();
        int i;
        long startTime = System.currentTimeMillis();

        // Thread creation
        pc_dynamic_task_stack task_stack = new pc_dynamic_task_stack(NUM_START, NUM_END, SIZE_OF_TASK);
        pc_dynamic_thread[] threads = new pc_dynamic_thread[NUM_THREADS];
        for (i=0; i<NUM_THREADS; i++) {
            threads[i] = new pc_dynamic_thread(i, NUM_END, SIZE_OF_TASK, task_stack, counter);
            threads[i].start();
        }
        for (i=0; i<NUM_THREADS; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ignored) {
                System.out.println("Thread joining failed.");
            }
        }
        long endTime = System.currentTimeMillis();
        long timeDiff = endTime - startTime;
        System.out.println("Program Execution Time : " + timeDiff + "ms");
        System.out.println("1..." + (NUM_END-1) + " prime# counter=" + counter.num_of_prime_numbers);
    }
}

class pc_dynamic_thread extends Thread {
    final int thread, num_end, size_of_task;
    pc_dynamic_counter counter;
    pc_dynamic_task_stack task_stack;
    public pc_dynamic_thread(
            int thread,
            int num_end,
            int size_of_task,
            pc_dynamic_task_stack task_stack,
            pc_dynamic_counter counter
    ) {
        this.thread = thread;
        this.num_end = num_end;
        this.size_of_task = size_of_task;
        this.task_stack = task_stack;
        this.counter = counter;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while (true) {
            OptionalInt get_task = task_stack.getTask();
            if (get_task.isEmpty()) break;
            int task = get_task.getAsInt();
            for (int i = task; i < Math.min(task + size_of_task, num_end); i++) {
                if (isPrime(i)) counter.addCount();
            }
        }
        long endTime = System.currentTimeMillis();
        long timeDiff = endTime - startTime;
        System.out.println("Thread " + thread + " Execution Time : " + timeDiff + "ms");
    }

    private static boolean isPrime(int x) {
        int i;
        if (x<=1) return false;
        for (i=2; i<x; i++) {
            if (x%i == 0) return false;
        }
        return true;
    }
}

class pc_dynamic_counter {
    int num_of_prime_numbers = 0;
    synchronized void addCount() {
        this.num_of_prime_numbers += 1;
    }
}

class pc_dynamic_task_stack {
    final int num_start, num_end, size_of_task;
    int current_task;

    public pc_dynamic_task_stack(int num_start, int num_end, int size_of_task) {
        this.num_start = num_start;
        this.num_end = num_end;
        this.size_of_task = size_of_task;
        this.current_task = num_start;
    }

    synchronized OptionalInt getTask() {
        if (current_task >= num_end) return OptionalInt.empty();
        current_task += size_of_task;
        return OptionalInt.of(current_task - size_of_task);
    }
}
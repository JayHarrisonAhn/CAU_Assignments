package problem1;

public class pc_static_cyclic {
    private static final int NUM_START = 0;
    private static int NUM_END = 200000;
    private static int NUM_THREADS = 4;
    private static final int SIZE_TASK = 10;

    public static void main (String[] args) {
        if (args.length == 2) {
            NUM_THREADS = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }
        pc_static_cyclic_counter counter = new pc_static_cyclic_counter();
        int i;
        long startTime = System.currentTimeMillis();

        // Thread creation
        pc_static_cyclic_thread[] threads = new pc_static_cyclic_thread[NUM_THREADS];
        for (i=0; i<NUM_THREADS; i++) {
            threads[i] = new pc_static_cyclic_thread(i, NUM_THREADS, SIZE_TASK, NUM_START, NUM_END, counter);
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

class pc_static_cyclic_thread extends Thread {
    int thread, num_of_threads, size_of_task, num_start, num_end;
    pc_static_cyclic_counter counter;
    public pc_static_cyclic_thread(
            int thread,
            int num_of_threads,
            int size_of_task,
            int num_start,
            int num_end,
            pc_static_cyclic_counter counter
    ) {
        this.thread = thread;
        this.num_of_threads = num_of_threads;
        this.size_of_task = size_of_task;
        this.num_start = num_start;
        this.num_end = num_end;
        this.counter = counter;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        for (int i=0; (i * size_of_task * num_of_threads) < num_end; i++) {
            for (int j=0; j < size_of_task; j++) {
                int number_to_check = (i * num_of_threads * size_of_task) + (size_of_task * thread) + j;
                if (number_to_check < num_end) {
                    if (isPrime(number_to_check)) counter.addCount();;
                } else {
                    break;
                }
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

class pc_static_cyclic_counter {
    int num_of_prime_numbers = 0;
    synchronized void addCount() {
        this.num_of_prime_numbers += 1;
    }
}
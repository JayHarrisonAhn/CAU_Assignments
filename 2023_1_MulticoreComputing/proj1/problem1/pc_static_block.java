package problem1;

public class pc_static_block {
    private static final int NUM_START = 0;
    private static int NUM_END = 200000;
    private static int NUM_THREADS = 4;

    public static void main (String[] args) {
        if (args.length == 2) {
            NUM_THREADS = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }
        pc_static_block_counter counter = new pc_static_block_counter();
        int i;
        long startTime = System.currentTimeMillis();

        // Thread creation
        pc_static_block_thread[] threads = new pc_static_block_thread[NUM_THREADS];
        for (i=0; i<NUM_THREADS; i++) {
            int thread_num_start = (int) (Math.ceil(NUM_START + ((NUM_END - NUM_START) * ((double) i / NUM_THREADS))));
            int thread_num_end = (int) (Math.ceil(NUM_START + ((NUM_END - NUM_START) * ((double) (i + 1) / NUM_THREADS))));
            threads[i] = new pc_static_block_thread(i, thread_num_start, thread_num_end, counter);
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

class pc_static_block_thread extends Thread {
    int thread, num_start, num_end;
    pc_static_block_counter counter;
    public pc_static_block_thread(int thread, int num_start, int num_end, pc_static_block_counter counter) {
        this.thread = thread;
        this.num_start = num_start;
        this.num_end = num_end;
        this.counter = counter;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        for (int i=num_start; i < num_end; i++) {
            if (isPrime(i)) counter.addCount();;
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

class pc_static_block_counter {
    int num_of_prime_numbers = 0;
    synchronized void addCount() {
        this.num_of_prime_numbers += 1;
    }
}
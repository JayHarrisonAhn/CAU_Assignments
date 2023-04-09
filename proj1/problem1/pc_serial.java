package problem1;

public class pc_serial {
    private static int NUM_END = 200000;
    private static int NUM_THREADS = 1;

    public static void main (String[] args) {
        if (args.length == 2) {
            NUM_THREADS = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }
        int counter = 0;
        int i;
        long startTime = System.currentTimeMillis();
        for (i=0; i<NUM_END; i++) {
            if (isPrime(i)) counter ++;
        }
        long endTime = System.currentTimeMillis();
        long timeDiff = endTime - startTime;
        System.out.println("Program Execution Time : " + timeDiff + "ms");
        System.out.println("1..." + (NUM_END-1) + " prime# counter=" + counter);
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
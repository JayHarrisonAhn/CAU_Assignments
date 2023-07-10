package problem2;

import java.util.Scanner;

// command-line execution example) java MatmultD 6 < mat500.txt
// 6 means the number of threads to use
// < mat500.txt means the file that contains two matrices is given as standard input
//
// In eclipse, set the argument value and file input by using the menu [Run]->[Run Configurations]->{[Arguments], [Common->Input File]}.

// Original JAVA source code: http://stackoverflow.com/questions/21547462/how-to-multiply-2-dimensional-arrays-matrix-multiplication
public class MatmultD
{
    private static Scanner sc = new Scanner(System.in);
    public static void main(String [] args)
    {
        int thread_no=0;
        if (args.length==1) thread_no = Integer.valueOf(args[0]);
        else thread_no = 1;

        int a[][]=readMatrix();
        int b[][]=readMatrix();

        long startTime = System.currentTimeMillis();
        MatmultD_Matrix_Result result_matrix = new MatmultD_Matrix_Result(a.length, b[0].length);

        // Thread creation
        MatmultD_Thread[] threads = new MatmultD_Thread[thread_no];
        for (int i=0; i<thread_no; i++) {
            threads[i] = new MatmultD_Thread(i, thread_no, a, b, result_matrix);
            threads[i].start();
        }
        for (int i=0; i<thread_no; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ignored) {
                System.out.println("Thread joining failed.");
            }
        }
        long endTime = System.currentTimeMillis();

        //printMatrix(a);
        //printMatrix(b);
        printMatrix(result_matrix.matrix);

        System.out.printf("[thread_no]:%2d , [Time]:%4d ms\n", thread_no, endTime-startTime);
    }

    public static int[][] readMatrix() {
        int rows = sc.nextInt();
        int cols = sc.nextInt();
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = sc.nextInt();
            }
        }
        return result;
    }

    public static void printMatrix(int[][] mat) {
        System.out.println("Matrix["+mat.length+"]["+mat[0].length+"]");
        int rows = mat.length;
        int columns = mat[0].length;
        int sum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.printf("%4d " , mat[i][j]);
                sum+=mat[i][j];
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Matrix Sum = " + sum + "\n");
    }
}

class MatmultD_Matrix_Result {
    int[][] matrix;

    public MatmultD_Matrix_Result(int row, int column) {
        this.matrix = new int[row][column];
    }

    synchronized void set_result(int row, int column, int result) {
        matrix[row][column] = result;
    }
}

class MatmultD_Thread extends Thread {
    final int thread, num_of_threads;
    final int a[][], b[][];
    MatmultD_Matrix_Result result_matrix;
    public MatmultD_Thread(
            int thread,
            int num_of_threads,
            int a[][],
            int b[][],
            MatmultD_Matrix_Result result_matrix
    ) {
        this.thread = thread;
        this.num_of_threads = num_of_threads;
        this.a = a;
        this.b = b;
        this.result_matrix = result_matrix;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        int n = a[0].length;
        int m = a.length;
        int p = b[0].length;

        for (int id_to_multiply = thread; id_to_multiply < m * p; id_to_multiply += num_of_threads) {
            int col = id_to_multiply % m;
            int row = (id_to_multiply - col) / m;

            int result = 0;
            for(int k=0; k<n; k++) {
                result += a[row][k] * b[k][col];
            }

            result_matrix.set_result(row, col, result);
        }
        long endTime = System.currentTimeMillis();
        System.out.printf("thread_no: %d\nCalculation Time: %d ms\n", thread, endTime-startTime);
    }
}
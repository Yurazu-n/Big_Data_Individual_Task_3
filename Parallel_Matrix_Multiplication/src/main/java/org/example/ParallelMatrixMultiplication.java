package org.example;

import java.util.stream.IntStream;

public class ParallelMatrixMultiplication {

    public ParallelMatrixMultiplication() {
    }

    public double[][] parallelStreamsMultiply(double[][] matrixA, double[][] matrixB){
        double[][] result = new double[matrixA.length][matrixB[0].length];
        int size =  matrixA.length;

        // Matrix multiplication using parallel streams
        IntStream.range(0, size).parallel().forEach(i -> {
            System.out.println("Executing thread: " + Thread.currentThread().getName());
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        });

        return result;
    }

    public static double[][] parallelThreadsMultiply(double[][] matrixA, double[][] matrixB) {
        int size = matrixA.length;
        double[][] result = new double[size][matrixB[0].length];

        // Create and launch threads for each row of the matrix
        Thread[] threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            final int row = i; // Each thread processes one row
            threads[i] = new Thread(() -> multiplyRow(matrixA, matrixB, result, row));
            threads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < size; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private static void multiplyRow(double[][] matrixA, double[][] matrixB, double[][] result, int row) {
        int size = matrixA.length;
        for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {
                result[row][j] += matrixA[row][k] * matrixB[k][j];
            }
        }
    }

}

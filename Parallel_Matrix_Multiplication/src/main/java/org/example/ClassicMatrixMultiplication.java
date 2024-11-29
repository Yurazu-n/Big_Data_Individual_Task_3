package org.example;

public class ClassicMatrixMultiplication {
    public ClassicMatrixMultiplication() {
    }

    public double[][] multiply(double[][] matrixA, double[][] matrixB) {

        int n = matrixA.length;

        double[][] result = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    result[i][j] = matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        return result;
    }
}

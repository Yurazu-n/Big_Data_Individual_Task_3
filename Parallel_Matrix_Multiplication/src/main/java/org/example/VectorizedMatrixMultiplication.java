package org.example;

public class VectorizedMatrixMultiplication {

    public VectorizedMatrixMultiplication() {
    }

    public double[][] vectorizedMatrixMultiply(double[][] matrixA, double[][] matrixB) {
        int n = matrixA.length;
        double[][] result = new double[n][matrixB[0].length];

        // Vectorized Matrix Multiplication
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < matrixB[0].length; j++) {
                double sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += matrixA[i][k] * matrixB[k][j];
                }
                result[i][j] = sum;
            }
        }

        return result;
    }

    // Optimized dot product calculation (could be used for vectorized processing)
    private static double vectorizedDotProduct(double[] row, double[] column) {
        double sum = 0.0;

        // Iterate over the row and column to compute the dot product
        for (int i = 0; i < row.length; i++) {
            sum += row[i] * column[i];
        }

        return sum;
    }

    // Efficient column extraction method
    private static double[] getColumn(double[][] matrix, int colIndex) {
        int rows = matrix.length;
        double[] column = new double[rows];
        for (int i = 0; i < rows; i++) {
            column[i] = matrix[i][colIndex];
        }
        return column;
    }

}

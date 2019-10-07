package com.matrix_multiply;

/**
 * Created by Siarhei Khalipau on 10/6/19.
 */
public class ThreadExecutor {

    private final int threadsCount;

    private int nextRow = 0, nextColumn = 0;

    public ThreadExecutor(int threadsCount) {
        if (threadsCount <= 0) {
            throw new IllegalArgumentException("Threads count must be a natural number!");
        }
        this.threadsCount = threadsCount;
    }

    public float [][] multiply(final float [][] matrix1, final float [][] matrix2) throws InterruptedException {
        if (matrix1 == null || matrix2 == null ||
                matrix1.length == 0 ||
                matrix1.length != matrix1[0].length ||
                matrix1.length != matrix2.length ||
                matrix2.length != matrix2[0].length) {
            throw new IllegalArgumentException("Invalid matrices!");
        }

        nextRow = nextColumn = 0;

        final int length = matrix1.length;
        float [][] m2Transp = new float[length][length];
        final float [][] result = new float[length][length];

        // Rows optimisation
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                m2Transp[i][j] = matrix2[j][i];
            }
        }

        Thread[] threads = new Thread[threadsCount];
        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new Thread(() -> {
                int row, column;
                while (nextRow < length) {
                    synchronized (result) {
                        row = nextRow;
                        column = nextColumn;
                        if ((nextColumn < length - 1)) {
                            nextColumn = nextColumn + 1;
                        } else {
                            nextColumn = 0;
                            nextRow = nextRow + 1;
                        }
                    }

                    if (row == length) {
                        break;
                    }

                    for (int j = 0; j < length; j++) {
                        result[row][column] += matrix1[row][j] * m2Transp[column][j];
                    }
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < threadsCount; i++) {
            threads[i].join();
        }

        return result;
    }
}

package com.matrix_multiply;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Siarhei Khalipau on 10/7/19.
 */
public class ThreadExecutorTest {

    @Test
    public void testMultiply() throws InterruptedException {
        ThreadExecutor threadExecutor = new ThreadExecutor(2);

        float [][] matrix1 = new float[1][1];
        float [][] matrix2 = new float[1][1];
        matrix1[0][0] = 1f;
        matrix2[0][0] = 1f;
        float [][] result = threadExecutor.multiply(matrix1, matrix2);
        assertEquals(1f, result[0][0], 0.00001f);

        matrix1 = new float[10][10];
        matrix2 = new float[10][10];
        matrix1[0][0] = 1f;
        matrix1[0][1] = 2f;
        matrix1[1][0] = 3f;
        matrix1[1][1] = 4f;

        matrix2[0][0] = 1f;
        matrix2[0][1] = 2f;
        matrix2[1][0] = 3f;
        matrix2[1][1] = 4f;

        matrix1[9][9] = 2f;
        matrix2[9][9] = 2f;

        result = threadExecutor.multiply(matrix1, matrix2);
        assertEquals(7f, result[0][0], 0.00001f);
        assertEquals(10f, result[0][1], 0.00001f);
        assertEquals(15f, result[1][0], 0.00001f);
        assertEquals(22f, result[1][1], 0.00001f);

        assertEquals(4f, result[9][9], 0.00001f);
        assertEquals(0f, result[8][8], 0.00001f);
    }
}

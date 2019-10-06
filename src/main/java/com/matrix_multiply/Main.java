package com.matrix_multiply;

import org.apache.log4j.Logger;

/**
 * Created by Siarhei Khalipau on 10/6/19.
 */
public class Main {

    private final static Logger LOG = Logger.getLogger(Main.class);

    public static void main(String [] args) {
        if (args.length == 0) {
            LOG.error("Matrix size is required!");
            System.exit(1);
        }

        int matrixSize = 1;
        int threadsCount = 10;

        try {
            matrixSize = Integer.valueOf(args[0]).intValue();
            threadsCount = args.length > 1 ? Integer.valueOf(args[1]).intValue() : 10;
        } catch (NumberFormatException nfe) {
            LOG.error("Matrix size and/or threads count should be integer!");
            System.exit(2);
        }

        LOG.info("Multiplying random square matrices with " + matrixSize + " size");

        try {
            MatrixBuilder matrixBuilder = MatrixBuilder.create(matrixSize)
                    .withMinRange(0f)
                    .withMaxRange(10f);
            float [][] matrix1 = matrixBuilder.build();
            float [][] matrix2 = matrixBuilder.build();

            ThreadExecutor threadExecutor = new ThreadExecutor(threadsCount);
            long start = System.currentTimeMillis();
            threadExecutor.multiply(matrix1, matrix2);
            long finish = System.currentTimeMillis();

            LOG.info(threadsCount + " threads: " + (finish - start) + " ms");

            threadExecutor = new ThreadExecutor(1);
            start = System.currentTimeMillis();
            threadExecutor.multiply(matrix1, matrix2);
            finish = System.currentTimeMillis();

            LOG.info("1 thread: " + (finish - start) + " ms");
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
            System.exit(3);
        }
    }
}

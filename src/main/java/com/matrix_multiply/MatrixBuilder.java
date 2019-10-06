package com.matrix_multiply;

import java.util.Random;

/**
 * Created by Siarhei Khalipau on 10/6/19.
 *
 * Builder for square random matrix with predefined size
 */
public class MatrixBuilder {

    private final static Random RANDOM = new Random();

    private final int size;

    private float min = Float.MIN_VALUE, max = Float.MAX_VALUE;

    private MatrixBuilder(int size) {
        this.size = size;
    }

    public MatrixBuilder withMinRange(float min) {
        this.min = min;
        return this;
    }

    public MatrixBuilder withMaxRange(float max) {
        this.max = max;
        return this;
    }

    public static MatrixBuilder create(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be a natural number!");
        }
        return new MatrixBuilder(size);
    }

    public float[][] build() {
        if (min >= max) {
            throw new IllegalArgumentException("Invalid range!");
        }

        float [][] result = new float[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = min + RANDOM.nextFloat() * (max - min);
            }
        }

        return result;
    }
}

package com.company;

import parcs.*;

public class Invert implements AM {
    public static final String MatrixKey = "Matrix";
    private double[][] matrix;
    private int pivotRow;
    private int startRow;
    private int endRow;

    public Invert(double[][] matrix, int pivotRow, int startRow, int endRow, boolean normalize) {
        this.matrix = matrix;
        this.pivotRow = pivotRow;
        this.startRow = startRow;
        this.endRow = endRow;
    }

    public void run(AMInfo amInfo) {
        matrix = (double[][]) amInfo.data.get(MatrixKey);
        startRow = amInfo.parent.readInt();
        endRow = amInfo.parent.readInt();
        pivotRow = amInfo.parent.readInt();

        int n = matrix.length;
        double pivot = matrix[pivotRow][pivotRow];

        for (int i = startRow; i < endRow; i++) {
            if (i == pivotRow) continue;
            double factor = matrix[i][pivotRow] / pivot;
            for (int j = pivotRow; j < 2 * n; j++) {
                matrix[i][j] -= factor * matrix[pivotRow][j];
            }
        }
    }
}
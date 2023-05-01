import parcs.*;

public class Invert implements AM {
    public void run(AMInfo amInfo) {
        int pivotNum = amInfo.parent.readInt();
        double[][] matrix = (double[][])amInfo.parent.readObject();
        double[] pivotRow = (double[])amInfo.parent.readObject();
        double pivot = pivotRow[pivotNum];

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i] == pivotRow) continue;
            double factor = matrix[i][pivotNum] / pivot;
            for (int j = pivotNum; j < pivotRow.length; j++) {
                matrix[i][j] -= factor * matrix[pivotNum][j];
            }
        }
        amInfo.parent.write(matrix);
    }
}
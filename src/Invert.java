import parcs.*;

public class Invert implements AM {
    @Override
    public void run(AMInfo amInfo) {
        //input
        int pivotNum = amInfo.parent.readInt();
        if (pivotNum < 0) {
            System.out.println("Error: pivotNum is negative. Please provide a valid index.");
            return;
        }

        double[][] matrix = (double[][])amInfo.parent.readObject();
        if (pivotNum >= matrix.length) {
            System.out.println("Error: pivotNum is greater than or equal to the number of rows in the matrix. Please provide a valid index.");
            return;
        }

        double[] pivotRow = (double[])amInfo.parent.readObject();

        double pivot = pivotRow[pivotNum];

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i] == pivotRow) continue;
            double factor = matrix[i][pivotNum] / pivot;
            for (int j = pivotNum; j < pivotRow.length; j++) {
                if (j >= matrix[i].length || j >= matrix[pivotNum].length) {
                    System.out.println("Error: Index j is out of bounds for the matrix. Please verify the matrix dimensions.");
                    return;
                }
                matrix[i][j] -= factor * matrix[pivotNum][j];
            }
        }

        amInfo.parent.write(matrix);
    }
}
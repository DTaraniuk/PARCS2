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

        for (var row:matrix) {
            if (row == pivotRow) continue;
            double factor = row[pivotNum] / pivot;
            for (int j = pivotNum; j < pivotRow.length; j++) {
                row[j] -= factor * pivotRow[j];
            }
        }

        amInfo.parent.write(matrix);
    }
}
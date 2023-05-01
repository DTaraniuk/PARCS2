import parcs.*;

public class Invert implements AM {
    @Override
    public void run(AMInfo amInfo) {
        //input
        int pivotNum = amInfo.parent.readInt();
        double[][] matrix = (double[][])amInfo.parent.readObject();
        double[] pivotRow = (double[])amInfo.parent.readObject();

        double pivot = pivotRow[pivotNum];

        for (var row:matrix) {
            System.out.println("row.l: " + row.length);
            System.out.println("pivotRow.l: " + pivotRow.length);
            if (row == pivotRow) continue;
            double factor = row[pivotNum] / pivot;
            for (int j = pivotNum; j < pivotRow.length; j++) {
                row[j] -= factor * pivotRow[j];
            }
        }

        amInfo.parent.write(matrix);
    }
}
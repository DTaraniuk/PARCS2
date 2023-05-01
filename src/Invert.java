import parcs.*;

public class Invert implements AM {
    public static final String MatrixKey = "Matrix";

    public Invert(){}

    public void run(AMInfo amInfo) {
        double[][] matrix = (double[][]) amInfo.data.get(MatrixKey);
        int startRow = amInfo.parent.readInt();
        int endRow = amInfo.parent.readInt();
        int pivotRow = amInfo.parent.readInt();

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
import parcs.*;

public class Invert implements AM {
    public void run(AMInfo amInfo) {
        double[][] matrix = null;
        var obj = amInfo.data.get("Matrix");
        if(obj instanceof double[][])
            matrix = (double[][])obj;
        else{
            System.out.println("matrix not an array");
        }
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
import parcs.*;

public class Invert implements AM {
    @Override
    public void run(AMInfo amInfo) {
        System.out.println("HERE");
        int pivotNum = amInfo.parent.readInt();
        double[][] matrix = (double[][])amInfo.parent.readObject();
        double[] pivotRow = (double[])amInfo.parent.readObject();
        for(var i:matrix){
            for(var j:i){
                System.out.println(j);
            }
        }
        for(var j:pivotRow){
            System.out.println(j);
        }
        System.out.println(pivotNum);
        System.out.println("HERE");
        double pivot = pivotRow[pivotNum];

        var newMatrix = matrix.clone();

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i] == pivotRow) continue;
            double factor = matrix[i][pivotNum] / pivot;
            for (int j = pivotNum; j < pivotRow.length; j++) {
                newMatrix[i][j] -= factor * matrix[pivotNum][j];
            }
        }
        amInfo.parent.write(newMatrix);
    }
}
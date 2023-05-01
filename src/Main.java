import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import parcs.*;

public class Main {
    private static final double EPSILON = 1e-10;

    public static void main(String[] args) {
        task t = new task();
        t.addJarFile("Invert.jar");
        new Main().run(new AMInfo(t, (channel)null));
        t.end();
    }

    public void run(AMInfo info) {
        //input
        var input = info.curtask.findFile("input");
        Scanner sc = null;
        try {
            sc = new Scanner(new File(input));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int n = sc.nextInt();
        sc.nextLine();
        int workers = sc.nextInt();
        sc.close();
        int step = (n - 1) / workers + 1;
        System.out.println("step:");
        System.out.println(step);
        System.out.println("n:");
        System.out.println(n);

        info.data = new HashMap();

        double[][] matrix = GenerateMatrix(n);

        PrintMatrix(matrix);

        // Create augmented matrix [A | I]
        double[][] augmentedMatrix = new double[n][2 * n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, augmentedMatrix[i], 0, n);
            augmentedMatrix[i][n + i] = 1;
        }

        for (int k = 0; k < n; k++) {
            System.out.println("k:" + k);

            // Find pivot row
            int maxRow = k;
            for (int i = k + 1; i < n; i++) {
                if (Math.abs(augmentedMatrix[i][k]) > Math.abs(augmentedMatrix[maxRow][k])) {
                    maxRow = i;
                }
            }

            // Check for singular matrix
            if (Math.abs(augmentedMatrix[maxRow][k]) < EPSILON) {
                System.out.println("Matrix is singular");
                return;
            }

            // Swap rows
            if (maxRow != k) {
                double[] temp = augmentedMatrix[k];
                augmentedMatrix[k] = augmentedMatrix[maxRow];
                augmentedMatrix[maxRow] = temp;
            }

            var channels = new channel[workers];
            var points = new point[workers];
            var pivotRow = augmentedMatrix[k];

            System.out.println("workers; " + workers);
            //divide elimination job between channels
            for (int i = 0; i < workers; i++) {
                var startPos = step*i;
                var endPos = Math.min((startPos + step), n); //calculate submatrix bounds

                System.out.println("point covering from " + startPos + " to " + endPos);

                var submatrix = new double[step][n];
                System.arraycopy(augmentedMatrix, startPos, submatrix, 0, endPos-startPos);//create submatrix for channel i to process

                var p = info.createPoint(); //create job and pass the arguments
                var c = p.createChannel();
                points[i] = p;
                channels[i] = c;
                points[i].execute("Invert");
                c.write(k);
                c.write(submatrix);
                c.write(pivotRow);
            }

            //collect results
            int end = 0;
            for(var channel : channels){
                var rows = (double[][])channel.readObject();
                if(rows == null || rows.length==0 || rows[0].length != 2*n){
                    System.out.println("matrix corrupted");
                    return;
                }
                System.arraycopy(rows, 0, augmentedMatrix, end, rows.length);
                end+=rows.length;
            }
            if(end!= augmentedMatrix.length){
                System.out.println("matrix corrupted");
                return;
            }

        }

        // Normalize diagonal elements
        for (int i = 0; i < n; i++) {
            var pivot = augmentedMatrix[i][i];
            for (int j = i; j < 2 * n; j++) {
                augmentedMatrix[i][j] /= pivot;
            }
        }

        PrintMatrix(augmentedMatrix);

        double[][] invertedMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(augmentedMatrix[i], n, invertedMatrix[i], 0, n);
        }

        if (invertedMatrix != null) {
            PrintMatrix(invertedMatrix);
        } else {
            System.out.println("Matrix is singular or not square.");
        }
    }

    private void PrintMatrix(double[][] matrix){
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.printf("%8.3f", value);
            }
            System.out.println();
        }
    }

    private double[][] GenerateMatrix(int n){
        var matrix = new double[n][n];
        var random = new Random();
        for(int i=0;i<n;++i){
            for(int j=0;j<n; ++j){
                matrix[i][j] = random.nextDouble();
            }
        }
        return matrix;
    }
}
import java.util.*;

import parcs.*;

public class Main implements AM {
    private static final double EPSILON = 1e-10;

    public static void main(String[] args) {
        task t = new task();
        t.addJarFile("Invert.jar");
        new Main().run(new AMInfo(t, (channel)null));
        t.end();
    }

    public void run(AMInfo info) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int workers = in.nextInt();
        int step = (n - 1) / workers + 1;

        double[][] matrix = GenerateMatrix(n);

        // Create augmented matrix [A | I]
        double[][] augmentedMatrix = new double[n][2 * n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, augmentedMatrix[i], 0, n);
            augmentedMatrix[i][n + i] = 1;
        }

        for (int k = 0; k < n; k++) {
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

            ArrayList<channel> channels = new ArrayList<channel>();
            info.data.put("Matrix", augmentedMatrix);

            for (int channel_num = 0; channel_num < n; channel_num += step) {
                point p = info.createPoint();
                channel c = p.createChannel();
                channels.add(c);
                p.execute("Invert");
                c.write(channel_num);
                c.write(channel_num + step);
                c.write(k);
            }

            augmentedMatrix = (double[][]) info.data.get(Invert.MatrixKey);
            if(augmentedMatrix.length!=n || augmentedMatrix[0].length != 2*n){
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

        double[][] invertedMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(augmentedMatrix[i], n, invertedMatrix[i], 0, n);
        }

        if (invertedMatrix != null) {
            for (double[] row : invertedMatrix) {
                for (double value : row) {
                    System.out.printf("%8.3f", value);
                }
                System.out.println();
            }
        } else {
            System.out.println("Matrix is singular or not square.");
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
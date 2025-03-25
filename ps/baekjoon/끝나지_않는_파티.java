import java.io.*;
import java.util.StringTokenizer;

public class 끝나지_않는_파티 {

    static int[][] d;
    static int N, M;

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            d = new int[N][N];
            for (int i = 0; i < N; ++i) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; ++j) {
                    d[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            for (int k = 0; k < N; ++k)
                for (int i = 0; i < N; ++i)
                    for (int j = 0; j < N; ++j)
                        d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);

            for (int i = 0; i < M; ++i) {
                st = new StringTokenizer(br.readLine());

                int A = Integer.parseInt(st.nextToken()) - 1;
                int B = Integer.parseInt(st.nextToken()) - 1;
                int C = Integer.parseInt(st.nextToken());

                if (d[A][B] <= C) System.out.println("Enjoy other party");
                else System.out.println("Stay here");
            }
        }
    }
}

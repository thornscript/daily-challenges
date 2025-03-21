import java.util.*;

public class 서울에서_경산까지 {
    
    static int N, K;
    static int[][] pt, pr;
    static int[][] dp;

    static int solve(int n, int k) {
        if (k > K) return Integer.MIN_VALUE / 2;
        if (n == N) return 0;

        if (dp[n][k] != -1) return dp[n][k];

        return dp[n][k] = Math.max(solve(n + 1, k + pt[n][0]) + pr[n][0],
        solve(n + 1, k + pt[n][1]) + pr[n][1]);
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            N = sc.nextInt();
            K = sc.nextInt();

            pt = new int[N][2];
            pr = new int[N][2];
            dp = new int[N][K + 1];

            for (int i = 0; i < N; i++) {
                pt[i][0] = sc.nextInt();
                pr[i][0] = sc.nextInt();
                pt[i][1] = sc.nextInt();
                pr[i][1] = sc.nextInt();
            }

            for (int[] row : dp) Arrays.fill(row, -1);

            System.out.println(solve(0, 0));
        }
    }
}


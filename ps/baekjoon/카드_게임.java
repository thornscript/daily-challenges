import java.io.*;
import java.util.StringTokenizer;

public class 카드_게임 {

    static final int MAX = 1000;
    // dp[left][right][turn]: turn==0 이면 근우, turn==1 이면 명우
    static int[][][] dp = new int[MAX][MAX][2];
    static int[] cards;

    static int solve(int left, int right, int turn) {
        if (left > right) return 0;
        if (dp[left][right][turn] != -1) return dp[left][right][turn];

        int res;
        if (turn == 1) res = Math.min(solve(left + 1, right, 1 - turn), solve(left, right - 1, 1 - turn));
        else res = Math.max(cards[left] + solve(left + 1, right, 1 - turn), cards[right] + solve(left, right - 1, 1 - turn));
        return dp[left][right][turn] = res;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            int t = Integer.parseInt(br.readLine().trim());
            while (t-- > 0) {
                int n = Integer.parseInt(br.readLine().trim());
                cards = new int[n];
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int i = 0; i < n; i++) cards[i] = Integer.parseInt(st.nextToken());

                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++)
                        dp[i][j][0] = dp[i][j][1] = -1;
                System.out.println(solve(0, n - 1, 0));
            }
        }
    }
}

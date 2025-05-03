import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 조_짜기 {

    static int N;
    static int[] scores;
    static int[][] dp = new int[1000][1000];

    static int solve(int idx, int groups) {
        if (idx == N) return 0;

        int d = dp[idx][groups];
        if (d != -1) return d;

        int minScore = scores[idx], maxScore = scores[idx];
        for (int i = idx + 1; i < N; ++i) {
            maxScore = Math.max(maxScore, scores[i]);
            minScore = Math.min(minScore, scores[i]);
            d = Math.max(d, solve(i + 1, groups + 1) + (maxScore - minScore));
        }

        return dp[idx][groups] = d;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            N = Integer.parseInt(br.readLine());
            scores = new int[N];

            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; ++i)
                scores[i] = Integer.parseInt(st.nextToken()); // 나이 기준 오름차순, 순서 변경 X
            
            for (int i = 0; i < N; ++i)
                for (int j = 0; j < N; ++j)
                    dp[i][j] = -1;
            
            System.out.println(solve(0, 0));
        }
    }
}

import java.io.*;
import java.util.*;

public class 양팔저울 {

    static int N;
    static int[] weights = new int[31];
    static boolean[][] dp = new boolean[31][15_001];

    static void solve(int cur, int weight) {
        if (cur > N || dp[cur][weight]) return;
        dp[cur][weight] = true;
        solve(cur + 1, weight + weights[cur]);
        solve(cur + 1, Math.abs(weight - weights[cur]));
        solve(cur + 1, weight);
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            N = Integer.parseInt(br.readLine()); // <= 30
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; ++i)
                weights[i] = Integer.parseInt(st.nextToken()); // <= 500, 오름차순
            
            solve(0, 0);
            
            int M = Integer.parseInt(br.readLine()); // <= 7
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; ++i) {
                int target = Integer.parseInt(st.nextToken()); // <= 40,000

                if (target > 15000) System.out.print("N "); // 추의 무게는 최대 500, N이 최대 30이므로 15000을 넘을 수 없음
                else if (dp[N][target]) System.out.print("Y ");
                else System.out.print("N ");
            }
        }
    }
}

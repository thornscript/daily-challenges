import java.io.*;
import java.util.*;

public class 장난감_조립 {

    static class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static int N, M;
    static int[] ind;
    static long[][] dp;
    static List<Pair>[] adj;

    static void solve() {
        for (int i = 1; i < N; ++i)
            if (ind[i] == 0)
                dp[i][i] = 1;
        
        Queue<Integer> q = new LinkedList<>();
        for (int i = 1; i < N; ++i)
            if (ind[i] == 0)
                q.add(i);
        
        while (!q.isEmpty()) {
            int y = q.poll();

            for (int i = 0; i < adj[y].size(); ++i) {
                Pair next = adj[y].get(i);

                for (int j = 1; j < N; ++j)
                    dp[next.x][j] += dp[y][j] * next.y;
                if (--ind[next.x] == 0)
                    q.add(next.x);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            N = Integer.parseInt(br.readLine());
            M = Integer.parseInt(br.readLine());
            adj = new ArrayList[N + 1];
            ind = new int[N + 1];
            dp = new long[N + 1][N + 1]; // i 부품을 만드는 데 j 기본 부품이 몇 개 필요한가

            for (int i = 1; i <= N; ++i)
                adj[i] = new ArrayList<>();

            for (int i = 0; i < M; ++i) {
                StringTokenizer st = new StringTokenizer(br.readLine());

                // X를 만드는데 Y가 K개 필요함
                int X = Integer.parseInt(st.nextToken());
                int Y = Integer.parseInt(st.nextToken());
                int K = Integer.parseInt(st.nextToken());

                adj[Y].add(new Pair(X, K));
                ind[X]++;
            }

            solve();

            for (int i = 1; i < N; ++i)
                if (dp[N][i] > 0)
                    System.out.println(String.format("%d %d", i, dp[N][i]));
        }
    }
}

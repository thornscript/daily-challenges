import java.util.*;
import java.io.*;

class 트리와_쿼리 {
    static boolean[] visited;
    static int[] cnt;
    static ArrayList<ArrayList<Integer>> g = new ArrayList<>();
    static int N, R, Q;

    static int dfs(int cur) {
        visited[cur] = true;
        cnt[cur] = 1;

        for (int next : g.get(cur)) {
            if (visited[next]) continue;
            cnt[cur] += dfs(next);
        }
        return cnt[cur];
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            R = Integer.parseInt(st.nextToken());
            Q = Integer.parseInt(st.nextToken());

            cnt = new int[N + 1];
            visited = new boolean[N + 1];
            for (int i = 0; i <= N; ++i) {
                g.add(new ArrayList<Integer>());
            }

            for (int i = 0; i < N - 1; ++i) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                g.get(u).add(v);
                g.get(v).add(u);
            }

            dfs(R);

            for (int i = 0; i < Q; ++i) {
                int u = Integer.parseInt(br.readLine());
                System.out.println(cnt[u]);
            }
        }
    }
}

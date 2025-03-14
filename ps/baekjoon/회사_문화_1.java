import java.io.*;
import java.util.*;

class 회사_문화_1 {
    static ArrayList<ArrayList<Integer>> v;

    private static void dfs(int cur, int root, int[] pls) {
        if (root != -1)
            pls[cur] += pls[root];
        for (int next : v.get(cur)) {
            if (next == root) continue;
            dfs(next, cur, pls);
        }
    }

    public static void main(String[] args) throws IOException {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            v = new ArrayList<ArrayList<Integer>>();

            for (int i = 0; i <= N; ++i)
                v.add(new ArrayList<>());

            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= N; ++i) {
                int u = Integer.parseInt(st.nextToken());
                if (u == -1) continue;
                v.get(i).add(u);
                v.get(u).add(i);
            }
            
            int[] pls = new int[N + 1];
            for (int i = 0; i < M; ++i) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                pls[u] += v;
            }

            // 1번이 항상 상사로 -1로 표현됨
            dfs(1, -1, pls);

            for (int i = 1; i <= N; ++i)
                System.out.print(pls[i] + " ");
        }
    }
}

import java.util.*;
import java.io.*;

public class 백채원 {

    static final int MAX_N = 200_000;
    static final int VIRTUAL_NODE = MAX_N + 1;
    static final int INF = (int) 2e8;

    static int N, M, K;
    static Map<Integer, List<Pair>> g = new HashMap<>();

    static class Pair {
        int a, b;

        Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    public static int[] dijkstra(int start) {
        int[] d = new int[VIRTUAL_NODE + 1];
        Arrays.fill(d, INF);
        d[start] = 0;

        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.a, b.a));
        pq.add(new Pair(0, start));

        while (!pq.isEmpty()) {
            Pair cur = pq.poll();

            if (cur.a > d[cur.b]) continue;

            for (int i = 0; i < g.get(cur.b).size(); ++i) {
                Pair next = g.get(cur.b).get(i);
                if (cur.a + next.b < d[next.a]) {
                    d[next.a] = cur.a + next.b;
                    pq.add(new Pair(d[next.a], next.a));
                }
            }
        }
        return d;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            for (int i = 1; i <= N; ++i)
                g.put(i, new ArrayList<>());

            for (int i = 0; i < M; ++i) {
                st = new StringTokenizer(br.readLine());

                int A = Integer.parseInt(st.nextToken());
                int B = Integer.parseInt(st.nextToken());
                int T = Integer.parseInt(st.nextToken());
                
                g.get(A).add(new Pair(B, T));
                g.get(B).add(new Pair(A, T));
            }

            st = new StringTokenizer(br.readLine());
            g.put(VIRTUAL_NODE, new ArrayList<>()); // 가상 노드 추가
            for (int i = 0; i < K; ++i) {
                int B = Integer.parseInt(st.nextToken());
                g.get(VIRTUAL_NODE).add(new Pair(B, 0));
            }

            int[] fromHome = dijkstra(VIRTUAL_NODE);
            int[] fromSchool = dijkstra(1);

            int cnt = 0;
            for (int i = 2; i <= N; ++i) {
                if (fromSchool[i] < fromHome[i]) {
                    System.out.print(i + " ");
                    cnt++;
                }
            }
            if (cnt == 0) System.out.println(0);
        }
    }
}

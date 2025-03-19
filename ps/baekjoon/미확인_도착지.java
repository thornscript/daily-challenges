import java.io.*;
import java.util.*;

public class 미확인_도착지 {
    static final int INF = (int) 1e8;
    static int n, m, t, s, g, h;
    static Map<Integer, List<Pair>> map;
    
    static class Pair implements Comparable<Pair> {
        int a, b;

        Pair(int a, int b) { this.a = a; this.b = b; }

        @Override
        public int compareTo(Pair o) {
            return this.a - o.a;
        }
    }

    static int[] dijkstra(int start) {
        int[] d = new int[n + 1];
        for (int i = 1; i <= n; ++i)
            d[i] = INF;
        d[start] = 0;

        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(0, start));

        while (!pq.isEmpty()) {
            Pair cur = pq.poll();

            if (cur.a > d[cur.b]) continue;

            for (Pair next : map.get(cur.b)) {
                int nextCost = next.b + cur.a;
                if (nextCost < d[next.a]) {
                    d[next.a] = nextCost;
                    pq.add(new Pair(nextCost, next.a));
                }
            }
        }
        return d;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int T = Integer.parseInt(br.readLine());

            while (T-- > 0) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                n = Integer.parseInt(st.nextToken());
                m = Integer.parseInt(st.nextToken());
                t = Integer.parseInt(st.nextToken());
                
                st = new StringTokenizer(br.readLine());
                s = Integer.parseInt(st.nextToken());
                g = Integer.parseInt(st.nextToken());
                h = Integer.parseInt(st.nextToken());

                int wei = 0;
                map = new HashMap<>();
                boolean[] checked = new boolean[n + 1];
                for (int i = 0; i < m; ++i) { // 도로
                    st = new StringTokenizer(br.readLine());

                    int start = Integer.parseInt(st.nextToken());
                    int dest = Integer.parseInt(st.nextToken());
                    int cost = Integer.parseInt(st.nextToken());
                    if ((start == g && dest == h) || (start == h && dest == g)) wei = cost;

                    if (!map.containsKey(start)) map.put(start, new ArrayList<>());
                    if (!map.containsKey(dest)) map.put(dest, new ArrayList<>());
                    map.get(start).add(new Pair(dest, cost));
                    map.get(dest).add(new Pair(start, cost));
                }
                for (int i = 0; i < t; ++i) // 목적지
                    checked[Integer.parseInt(br.readLine())] = true;

                int[] minDistS = dijkstra(s);
                if (minDistS[g] > minDistS[h]) {
                    int temp = g;
                    g = h;
                    h = temp;
                }
                int[] minDistH = dijkstra(h);
                for (int i = 1; i <= n; ++i) {
                    if (!checked[i]) continue;
                    if (minDistS[g] + wei + minDistH[i] == minDistS[i]) System.out.print(i + " ");
                }
                System.out.println();
            }
        }
    }
}

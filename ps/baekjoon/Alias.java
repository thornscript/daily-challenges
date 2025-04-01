import java.io.*;
import java.util.*;

public class Alias {

    static int n, m;
    static Map<String, List<Pair>> g = new HashMap<>();

    static class Pair {
        String x;
        long t;

        Pair(String x, long t) {
            this.x = x;
            this.t = t;
        }
    }

    static Map<String, Long> solve(String start) {
        Map<String, Long> d = new HashMap<>();
        d.put(start, 0L);

        PriorityQueue<Pair> pq = new PriorityQueue<>((Pair p1, Pair p2) -> Long.compare(p1.t, p2.t));
        pq.add(new Pair(start, 0L));

        while (!pq.isEmpty()) {
            Pair cur = pq.poll();

            d.putIfAbsent(cur.x, (long) 1e15);
            if (cur.t > d.get(cur.x) || !g.containsKey(cur.x)) continue;

            for (int i = 0; i < g.get(cur.x).size(); ++i) {
                String nextWord = g.get(cur.x).get(i).x;
                long nextCost = cur.t + g.get(cur.x).get(i).t;
                d.putIfAbsent(nextWord, (long) 1e15);
                if (nextCost < d.get(nextWord)) {
                    d.put(nextWord, nextCost);
                    pq.add(new Pair(nextWord, nextCost));
                }
            }
        }
        return d;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());

            for (int i = 0; i < m; ++i) {
                st = new StringTokenizer(br.readLine());

                String xi = st.nextToken();
                String yi = st.nextToken();
                int ti = Integer.parseInt(st.nextToken());

                g.putIfAbsent(xi, new ArrayList<>());
                g.get(xi).add(new Pair(yi, ti));
            }

            int q = Integer.parseInt(br.readLine());
            for (int i = 0; i < q; ++i) {
                st = new StringTokenizer(br.readLine());

                String ai = st.nextToken();
                String bi = st.nextToken();

                Map<String, Long> fromAi = solve(ai);
                System.out.println(fromAi.get(bi) == null ? "Roger" : fromAi.get(bi));
            }
        }
    }
}

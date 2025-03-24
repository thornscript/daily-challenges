import java.io.*;
import java.util.*;

public class 문제집 {

    static int N, M;
    static int[] indegree;
    static Map<Integer, List<Integer>> g;

    static class Problem implements Comparable<Problem> {
        int ease;

        Problem(int ease) {
            this.ease = ease;
        }

        @Override
        public int compareTo(Problem o) {
            return this.ease - o.ease;
        }
    }

    static void solve() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int i = 1; i <= N; ++i)
            if (indegree[i] == 0)
                pq.add(i);
        
        while (!pq.isEmpty()) {
            int cur = pq.poll();

            System.out.print(cur + " ");

            for (int i = 0; i < g.get(cur).size(); ++i) {
                int b = g.get(cur).get(i);

                indegree[b]--;
                if (indegree[b] == 0)
                    pq.add(b);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            g = new HashMap<>();
            for (int i = 1; i <= N; ++i)
                g.put(i, new ArrayList<>());

            indegree = new int[N + 1];
            for (int i = 0; i < M; ++i) {
                st = new StringTokenizer(br.readLine());

                int A = Integer.parseInt(st.nextToken());
                int B = Integer.parseInt(st.nextToken());

                g.get(A).add(B);
                indegree[B]++;
            }

            solve();
        }
    }
}

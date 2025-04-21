import java.util.*;
import java.io.*;

public class League_of_Overwatch_at_Moloco_Hard {

    static int N, M;
    static int[] colors;
    static Map<Integer, List<Integer>> g = new HashMap<>();

    public static boolean solve() {
        boolean possible = true;
        for (int i = 1; i <= N && possible; ++i) {
            if (colors[i] == 0) {
                Queue<Integer> q = new ArrayDeque<>();
                q.add(i);
                colors[i] = 1;

                while (!q.isEmpty() && possible) {
                    int cur = q.poll();

                    for (int neighbor : g.get(cur)) {
                        if (colors[neighbor] == 0) {
                            colors[neighbor] = -colors[cur];
                            q.add(neighbor);
                        } else if (colors[neighbor] == colors[cur]) {
                            possible = false;
                            break;
                        }
                    }
                }
            }
        }
        return possible;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            colors = new int[N + 1];

            for  (int i = 1; i <= N; ++i)
                g.put(i, new ArrayList<>());

            for (int i = 0; i < M; ++i) {
                st = new StringTokenizer(br.readLine());

                int fi = Integer.parseInt(st.nextToken());
                int si = Integer.parseInt(st.nextToken());

                g.get(fi).add(si);
                g.get(si).add(fi);
            }

            System.out.println(solve() ? "POSSIBLE" : "IMPOSSIBLE");
        }
    }
}

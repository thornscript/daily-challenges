import java.io.*;
import java.util.*;

public class 스타트_택시 {

    static int N, M, F;
    static int[] di = {-1, 0, 0, 1}, dj = {0, -1, 1, 0};
    static int[][] map;
    static Map<Pair, Pair> dest = new HashMap<>();

    static class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public boolean equals(Object ot) {
            if (ot == null || this.getClass() != ot.getClass()) return false;
            if (this == ot) return true;
            Pair o = (Pair) ot;
            return this.x == o.x && this.y == o.y;
        }
    }

    static class Triple {
        int x, y, z;

        Triple(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    static Triple findPassenger(int si, int sj) {
        boolean[][] visited = new boolean[N + 1][N + 1];
        visited[si][sj] = true;

        Queue<Triple> q = new LinkedList<>();
        q.add(new Triple(si, sj, 0));

        if (dest.containsKey(new Pair(si, sj)))
            return new Triple(si, sj, 0);

        int foundDist = -1;
        List<Pair> cand = new ArrayList<>();

        while (!q.isEmpty()) {
            Triple cur = q.poll();

            if (foundDist != -1 && cur.z > foundDist) break;

            for (int i = 0; i < 4; ++i) {
                int ni = cur.x + di[i], nj = cur.y + dj[i];

                if (!(ni >= 1 && ni <= N && nj >= 1 && nj <= N) || visited[ni][nj]) continue;
                if (map[ni][nj] == 1) continue; // 벽

                if (dest.containsKey(new Pair(ni, nj))) {
                    int nz = cur.z + 1;
                    if (foundDist == -1) {
                        foundDist = cur.z + 1;
                        cand.add(new Pair(ni, nj));
                    } else if (nz == foundDist) cand.add(new Pair(ni, nj));
                }
                
                visited[ni][nj] = true;
                q.add(new Triple(ni, nj, cur.z + 1));
            }
        }
        if (cand.isEmpty()) return new Triple(0, 0, -1);

        cand.sort((a, b) -> a.x != b.x ? a.x - b.x : a.y - b.y);
        Pair pick = cand.get(0);
        return new Triple(pick.x, pick.y, foundDist);
    }

    static int start(int si, int sj, int fi, int fj) {
        boolean[][] visited = new boolean[N + 1][N + 1];
        visited[si][sj] = true;

        Queue<Triple> q = new LinkedList<>();
        q.add(new Triple(si, sj, 0));

        while (!q.isEmpty()) {
            Triple cur = q.poll();

            if (cur.x == fi && cur.y == fj) return cur.z;

            for (int i = 0; i < 4; ++i) {
                int ni = cur.x + di[i], nj = cur.y + dj[i];

                if (!(ni >= 1 && ni <= N && nj >= 1 && nj <= N) || visited[ni][nj]) continue;
                if (map[ni][nj] == 1) continue; // 벽

                visited[ni][nj] = true;
                q.add(new Triple(ni, nj, cur.z + 1));
            }
        }
        return -1;
    }

    static void fail() {
        System.out.println("-1");
        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            F = Integer.parseInt(st.nextToken());

            map = new int[N + 1][N + 1];
            for (int i = 1; i <= N; ++i) {
                st = new StringTokenizer(br.readLine());
                for (int j = 1; j <= N; ++j)
                    map[i][j] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            int si = Integer.parseInt(st.nextToken());
            int sj = Integer.parseInt(st.nextToken());

            for (int i = 0; i < M; ++i) {
                st = new StringTokenizer(br.readLine());
                int psi = Integer.parseInt(st.nextToken());
                int psj = Integer.parseInt(st.nextToken());
                int pfi = Integer.parseInt(st.nextToken());
                int pfj = Integer.parseInt(st.nextToken());
                dest.put(new Pair(psi, psj), new Pair(pfi, pfj));
            }

            for (int i = 0; i < M; ++i) {
                Triple d = findPassenger(si, sj); // 택시 -> 승객
                if (d.z == -1 || d.z > F) fail();
                F -= d.z;
                si = d.x;
                sj = d.y;

                Pair t = dest.get(new Pair(d.x, d.y)); // 승객 -> 목적지
                int dist = start(si, sj, t.x, t.y);
                if (dist == -1 || dist > F) fail();

                dest.remove(new Pair(d.x, d.y));
                si = t.x;
                sj = t.y;
                F += dist;
            }

            System.out.println(F);
        }
    }
}

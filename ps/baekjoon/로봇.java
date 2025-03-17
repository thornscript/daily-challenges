import java.util.*;

public class 로봇 {

    private static int[] dr = {0, 0, 0, 1, -1};
    private static int[] dc = {0, 1, -1, 0, 0};

    private static int turn_left(int d) {
        if (d == 1) return 4;
        else if (d == 2) return 3;
        else if (d == 3) return 1;
        else return 2;
    }

    private static int turn_right(int d) {
        if (d == 1) return 3;
        else if (d == 2) return 4;
        else if (d == 3) return 2;
        else return 1;
    }

    private static class State {
        int r, c, dir, cnt;

        public State(int r, int c, int dir, int cnt) {
            this.r = r;
            this.c = c;
            this.dir = dir;
            this.cnt = cnt;
        }

        public boolean equals(State other) {
            return this.r == other.r && this.c == other.c && this.dir == other.dir;
        }
    }

    private static int bfs(State src, State dst, int[][] Map, int N, int M) {
        Queue<State> que = new LinkedList<>();
        boolean[][][] visited = new boolean[N + 1][M + 1][5];

        que.add(src);
        visited[src.r][src.c][src.dir] = true;

        while (!que.isEmpty()) {
            State now = que.poll();

            if (now.equals(dst)) {
                return now.cnt;
            }

            for (int k = 1; k <= 3; k++) {
                int nr = now.r + dr[now.dir] * k;
                int nc = now.c + dc[now.dir] * k;

                if (nr < 1 || nr > N || nc < 1 || nc > M || Map[nr][nc] == 1) break;
                if (visited[nr][nc][now.dir]) continue;
                visited[nr][nc][now.dir] = true;
                que.add(new State(nr, nc, now.dir, now.cnt + 1));
            }

            int td = turn_left(now.dir);
            if (!visited[now.r][now.c][td]) {
                visited[now.r][now.c][td] = true;
                que.add(new State(now.r, now.c, td, now.cnt + 1));
            }

            td = turn_right(now.dir);
            if (!visited[now.r][now.c][td]) {
                visited[now.r][now.c][td] = true;
                que.add(new State(now.r, now.c, td, now.cnt + 1));
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int N = sc.nextInt();
            int M = sc.nextInt();

            int[][] Map = new int[N + 1][M + 1];
            for (int i = 1; i <= N; i++)
                for (int j = 1; j <= M; j++)
                    Map[i][j] = sc.nextInt();

            int sr = sc.nextInt();
            int scc = sc.nextInt();
            int sdir = sc.nextInt();
            State src = new State(sr, scc, sdir, 0);

            int dr = sc.nextInt();
            int dc = sc.nextInt();
            int ddir = sc.nextInt();
            State dst = new State(dr, dc, ddir, 0);

            int result = bfs(src, dst, Map, N, M);
            System.out.println(result);
        }
    }
}

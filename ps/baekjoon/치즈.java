import java.util.*;
import java.io.*;

public class 치즈 {

    static int N, M;
    static int[][] grid;
    static int[] di = {0, 0, 1, -1}, dj = {1, -1, 0, 0};

    static class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static void melt() {
        boolean[][] visited = new boolean[N][M];
        int[][] cnt = new int[N][M];
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(0, 0));

        while (!q.isEmpty()) {
            Pair cur = q.poll();

            for (int i = 0; i < 4; ++i) {
                int ni = cur.x + di[i], nj = cur.y + dj[i];

                if (!(ni >= 0 && ni < N && nj >= 0 && nj < M) || visited[ni][nj]) continue;
                if (grid[ni][nj] == 1) {
                    cnt[ni][nj]++;
                    continue;
                }
                visited[ni][nj] = true;
                q.add(new Pair(ni, nj));
            }
        }

        for (int i = 0; i < N; ++i)
            for (int j = 0; j < M; ++j)
                if (cnt[i][j] >= 2)
                    grid[i][j] = 0;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            grid = new int[N][M];
            for (int i = 0; i < N; ++i) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < M; ++j)
                    grid[i][j] = Integer.parseInt(st.nextToken());
            }

            int time = 0;
            while (true) {
                boolean isEnd = true;
                for (int i = 0; i < N && isEnd; ++i)
                    for (int j = 0; j < M && isEnd; ++j)
                        if (grid[i][j] > 0)
                            isEnd = false;
                if (isEnd) {
                    System.out.println(time);
                    return;
                }
                melt();
                time++;
            }
        }
    }
}

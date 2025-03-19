import java.io.*;
import java.util.StringTokenizer;

public class 마법사_상어와_토네이도 {
    static int[][][] ROTATION = {
        {
            {0, 0, 2, 0, 0},
            {0, 10, 7, 1, 0},
            {5, 0, 0, 0, 0},
            {0, 10, 7, 1, 0},
            {0, 0, 2, 0, 0}
        },
        {
            {0, 0, 0, 0, 0},
            {0, 1, 0, 1, 0},
            {2, 7, 0, 7, 2},
            {0, 10, 0, 10, 0},
            {0, 0, 5, 0, 0}
        },
        {
            {0, 0, 2, 0, 0},
            {0, 1, 7, 10, 0},
            {0, 0, 0, 0, 5},
            {0, 1, 7, 10, 0},
            {0, 0, 2, 0, 0}
        },
        {
            {0, 0, 5, 0, 0},
            {0, 10, 0, 10, 0},
            {2, 7, 0, 7, 2},
            {0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0}
        }
    }; // {2, 2} = START
    static int[] di = {0, 1, 0, -1}, dj = {-1, 0, 1, 0}; // 서, 남, 동, 북
    static int N, ans;
    static int[][] grid;

    static void spread(int ci, int cj, int dir) {
        int remain = grid[ci][cj];
        if (remain == 0) return;

        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                int ti = ci + i - 2, tj = cj + j - 2;
                int d = (int)(grid[ci][cj] * (ROTATION[dir][i][j] / 100.0));
                if (!(ti >= 0 && ti < N && tj >= 0 && tj < N)) ans += d;
                else grid[ti][tj] += d;
                remain -= d;
            }
        }

        // 남은 모래를 이동시킴
        int ti = ci + di[dir], tj = cj + dj[dir];
        if (!(ti >= 0 && ti < N && tj >= 0 && tj < N)) ans += remain;
        else grid[ti][tj] += remain;

        grid[ci][cj] = 0;
    }

    static void simulate(int si, int sj) {
        int step = 1, cnt = 0, dir = 0;

        while (true) {
            for (int i = 0; i < step; ++i) {
                si += di[dir];
                sj += dj[dir];
                spread(si, sj, dir);
                if (si == 0 && sj == 0) return;
            }
            dir = (dir + 1) % 4;
            if (++cnt % 2 == 0) step++;
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            N = Integer.parseInt(br.readLine());
            
            grid = new int[N][N];
            for (int i = 0; i < N; ++i) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; ++j)
                    grid[i][j] = Integer.parseInt(st.nextToken());
            }

            simulate(N / 2, N / 2);
            System.out.println(ans);
        }
    }
}

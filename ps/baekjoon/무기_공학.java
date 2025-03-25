import java.util.*;

public class 무기_공학 {

    static int N, M, ans;
    static int[][] blocks;
    static boolean[][] checked;
    static int[][] shapes = { {1, 2, 0, 1}, {0, 1, 1, 2}, {1, 0, 2, 1}, {2, 1, 1, 0} };

    static void solve(int ci, int cj, int score) {
        if (ci == N - 1) { ans = Math.max(ans, score); return; }
        if (cj == M - 1) { solve(ci + 1, 0, score); return; }

        for (int i = 0; i < 4; ++i) { // shapes
            boolean isPossible = true;
            // 배치가 가능한지 확인함
            for (int j = 0; j < 2 && isPossible; ++j) {
                for (int k = 0; k < 2 && isPossible; ++k) {
                    int ni = ci + j, nj = cj + k;
                    if (!(ni >= 0 && ni < N && nj >= 0 && nj < M) || (shapes[i][j * 2 + k] != 0 && checked[ni][nj])) {
                        isPossible = false;
                        break;
                    }
                }
            }

            if (isPossible) { // 배치가 가능하면 그대로 배치
                int addedScore = 0;
                for (int j = 0; j < 2; ++j) {
                    for (int k = 0; k < 2; ++k) {
                        addedScore += blocks[ci + j][cj + k] * shapes[i][j * 2 + k];
                        if (shapes[i][j * 2 + k] != 0) checked[ci + j][cj + k] = true;
                    }
                }
                solve(ci, cj + 1, score + addedScore);
                for (int j = 0; j < 2; ++j)
                    for (int k = 0; k < 2; ++k)
                        if (shapes[i][j * 2 + k] != 0) checked[ci + j][cj + k] = false;
            }
        }
        // 아무것도 안하고 그냥 넘어감
        solve(ci, cj + 1, score);
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            blocks = new int[N][M];
            checked = new boolean[N][M];
            for (int i = 0; i < N; ++i) {
                st = new StringTokenizer(sc.nextLine());
                for (int j = 0; j < M; ++j)
                    blocks[i][j] = Integer.parseInt(st.nextToken());
            }

            solve(0, 0, 0);
            System.out.println(ans);
        }
    }
}

import java.util.*;

public class 빌런_호석 {
    static int[][] state = {{1, 1, 1, 0, 1, 1, 1}, {0, 0, 1, 0, 0, 1, 0}, {1, 0, 1, 1, 1, 0, 1}, {1, 0, 1, 1, 0, 1, 1}, {0, 1, 1, 1, 0, 1, 0}, {1, 1, 0, 1, 0, 1, 1}, {1, 1, 0, 1, 1, 1, 1}, {1, 0, 1, 0, 0, 1, 0}, {1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 0, 1, 1}};
    static int[][] c = new int[10][10];
    static int N, K, P, X, ans = 0;

    static void preprocess() {
        for (int i = 0; i <= 9; ++i) {
            for (int j = i + 1; j <= 9; ++j) {
                int cnt = 0;
                for (int k = 0; k <= 6; ++k)
                    if (state[i][k] != state[j][k])
                        cnt++;
                c[i][j] = c[j][i] = cnt;
            }
        }
    }

    static void solve(int curDigit, int remain, int finalFloor) {
        if (remain < 0 || finalFloor > N) return;
        if (curDigit <= 0) { if (remain == P || finalFloor == 0) return; ans++; return; }

        int x = X / (int) Math.pow(10, curDigit - 1) % 10;
        for (int i = 0; i <= 9; ++i) {
            solve(curDigit - 1, remain - c[x][i], finalFloor + i * (int) Math.pow(10, curDigit - 1));
        }
    }

    public static void main(String[] args) {
        // 최대 K자리 수, 1층부터 N층까지, X층에 멈춰있을 때 최대 P개를 반전시킬 수 있음
        try (Scanner sc = new Scanner(System.in)) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());

            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            P = Integer.parseInt(st.nextToken());
            X = Integer.parseInt(st.nextToken());

            preprocess();
            solve(K, P, 0);

            System.out.println(ans);
        }
    }
}

import java.util.*;

public class 감시 {

    static int N, M, ans = 64;
    static int[][] arr, checked;
    static int[][][][] pat = { {},
        { // [1]
            {{0, 1}}, {{1, 0}}, {{0, -1}}, {{-1, 0}}
        },
        { // [2]
            {{0, 1}, {0, -1}}, {{-1, 0}, {1, 0}}
        },
        { // [3]
            {{-1, 0}, {0, 1}}, {{0, 1}, {1, 0}}, {{0, -1}, {1, 0}}, {{0, -1}, {-1, 0}}
        },
        { // [4]
            {{0, 1}, {0, -1}, {-1, 0}}, {{-1, 0}, {0, 1}, {1, 0}}, {{0, -1}, {0, 1}, {1, 0}}, {{-1, 0}, {0, -1}, {1, 0}}
        },
        { // [5]
            {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}
        }
    };
    static List<Cctv> cctvs = new ArrayList<>();

    static class Cctv {
        int ci, cj, type;

        Cctv(int ci, int cj, int type) {
            this.ci = ci;
            this.cj = cj;
            this.type = type;
        }
    }

    static void solve(int idx) {
        if (idx >= cctvs.size()) {
            int r = 0;
            for (int i = 0; i < N; ++i)
                for (int j = 0; j < M; ++j)
                    r += (arr[i][j] == 0 && checked[i][j] == 0 ? 1 : 0);
            ans = Math.min(r, ans);
            return;
        }

        int type = cctvs.get(idx).type;
        for (int i = 0; i < pat[type].length; ++i) {
            increaseCount(cctvs.get(idx), i, 1);
            solve(idx + 1);
            increaseCount(cctvs.get(idx), i, -1);
        }
    }

    static void increaseCount(Cctv cctv, int dir, int amount) {
        int c = 1;
        checked[cctv.ci][cctv.cj] += amount;
        boolean[] temp = new boolean[4];
        while (true) {
            boolean flag = false;
            for (int i = 0; i < pat[cctv.type][dir].length; ++i) {
                if (temp[i]) continue;
                int ni = cctv.ci + pat[cctv.type][dir][i][0] * c;
                int nj = cctv.cj + pat[cctv.type][dir][i][1] * c;
                if (!(ni >= 0 && ni < N && nj >= 0 && nj < M) || arr[ni][nj] == 6) { temp[i] = true; continue; }
                checked[ni][nj] += amount;
                flag = true;
            }
            c++;
            if (!flag) break;
        }
    }
    
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            N = sc.nextInt();
            M = sc.nextInt();

            arr = new int[N][M];
            checked = new int[N][M];
            for (int i = 0; i < N; ++i) {
                for (int j = 0; j < M; ++j) {
                    arr[i][j] = sc.nextInt();
                    if (arr[i][j] >= 1 && arr[i][j] <= 5)
                        cctvs.add(new Cctv(i, j, arr[i][j]));
                }
            }

            solve(0);
            System.out.println(ans);
        }            
    }
}

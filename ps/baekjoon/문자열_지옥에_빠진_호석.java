import java.io.*;
import java.util.*;

public class 문자열_지옥에_빠진_호석 {

    static char[][] board;
    static int N, M;
    static int[] di = {-1, -1, -1, 0, 0, 1, 1, 1}, dj = {-1, 0, 1, -1, 1, -1, 0, 1};
    static Map<String, Integer> cnt = new HashMap<>();

    static void dfs(int ci, int cj, String str) {
        if (!cnt.containsKey(str)) cnt.put(str, 0);
        cnt.put(str, cnt.get(str) + 1);
        if (str.length() >= 5)
            return;
        for (int i = 0; i < 8; ++i) {
            int ni = ci + di[i], nj = cj + dj[i];
            if (!(ni >= 0 && ni < N)) ni = (ni + N) % N;
            if (!(nj >= 0 && nj < M)) nj = (nj + M) % M;
            dfs(ni, nj, str + board[ni][nj]);
        }
    }

    static void solve() {
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < M; ++j)
                dfs(i, j, Character.toString(board[i][j]));
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            board = new char[N][M];
            for (int i = 0; i < N; ++i)
                board[i] = br.readLine().toCharArray();
            
            solve();
            
            for (int i = 0; i < K; ++i) {
                String q = br.readLine();
                System.out.println(cnt.getOrDefault(q, 0));
            }
        }
    }
}

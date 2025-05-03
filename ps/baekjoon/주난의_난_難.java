import java.io.*;
import java.util.*;

public class 주난의_난_難 {

    static int N, M, x1, y1, x2, y2;
    static int[] di = {0, 0, 1, -1}, dj = {1, -1, 0, 0};
    static char[][] board;

    static class Pair {
        int a, b;

        Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    static boolean simulate() {
        boolean[][] visited = new boolean[N][M];

        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(x1, y1));
        visited[x1][y1] = true;

        while (!q.isEmpty()) {
            Pair cur = q.poll();

            for (int i = 0; i < 4; ++i) {
                int ni = cur.a + di[i], nj = cur.b + dj[i];

                if (!(ni >= 0 && ni < N && nj >= 0 && nj < M) || visited[ni][nj]) continue;

                if (board[ni][nj] == '#') return true;
                visited[ni][nj] = true;
                if (board[ni][nj] == '1') {
                    board[ni][nj] = '0';
                    continue;
                }
                q.add(new Pair(ni, nj));
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            x1 = Integer.parseInt(st.nextToken()) - 1; // 0-based
            y1 = Integer.parseInt(st.nextToken()) - 1;
            x2 = Integer.parseInt(st.nextToken()) - 1;
            y2 = Integer.parseInt(st.nextToken()) - 1;

            board = new char[N][M];
            for (int i = 0; i < N; ++i)
                board[i] = br.readLine().toCharArray();
            
            int turns = 1;
            while (true) {
                if (simulate()) { System.out.println(turns); break; }
                else turns++;
            }
        }
    }
}

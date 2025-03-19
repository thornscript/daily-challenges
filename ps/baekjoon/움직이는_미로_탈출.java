import java.util.*;

public class 움직이는_미로_탈출 {
    static char[][] board = new char[8][8];
    static Queue<State> q = new LinkedList<>();
    static int[] di = {-1, -1, -1, 0, 0, 0, 1, 1, 1}, dj = {-1, 0, 1, -1, 0, 1, -1, 0, 1};

    static class State {
        int ci, cj, state;
        
        State(int ci, int cj, int state) {
            this.ci = ci;
            this.cj = cj;
            this.state = state;
        }
    }

    public static int solve() {
        while (!q.isEmpty()) {
            State cur = q.poll();

            if (cur.state == 0) { // 욱제
                if (board[cur.ci][cur.cj] == '#') continue;
                for (int i = 0; i < 9; ++i) {
                    int ni = cur.ci + di[i], nj = cur.cj + dj[i];
                    if (!(ni >= 0 && ni < 8 && nj >= 0 && nj < 8) || board[ni][nj] == '#') continue;
                    if (ni == 0 && nj == 7) return 1;
                    q.add(new State(ni, nj, 0));
                }
            } else { // 블럭
                int ni = cur.ci + 1, nj = cur.cj;
                if (ni < 0 || ni >= 8) continue;
                board[cur.ci][cur.cj] = '.';
                board[ni][nj] = '#';
                q.add(new State(ni, nj, 1));
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            q.add(new State(7, 0, 0));
            for (int i = 0; i < 8; ++i)
                board[i] = sc.nextLine().toCharArray();
            for (int i = 7; i >= 0; --i)
                for (int j = 0; j < 8; ++j)
                    if (board[i][j] == '#')
                        q.add(new State(i, j, 1));
            System.out.println(solve());
        }
    }
}

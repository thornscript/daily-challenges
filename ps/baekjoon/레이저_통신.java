import java.util.*;
import java.io.*;

public class 레이저_통신 {

    static class Pos {
        int x, y;
        Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Laser {
        Pos l;
        int cnt;
        Laser(Pos l, int cnt) {
            this.l = l;
            this.cnt = cnt;
        }
    }

    static int[] di = { -1, 0, 1, 0 }; // 북, 동, 남, 서
    static int[] dj = { 0, 1, 0, -1 };

    static int W, H;
    static char[][] map;
    static boolean[][] visited;
    static List<Pos> cPos = new ArrayList<>();
    static int res;
    
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());
            
            map = new char[H][W];
            visited = new boolean[H][W];

            for (int i = 0; i < H; ++i) {
                map[i] = br.readLine().toCharArray();
                for (int j = 0; j < W; ++j)
                    if (map[i][j] == 'C')
                        cPos.add(new Pos(i, j));
            }

            Laser start = new Laser(new Pos(cPos.get(0).x, cPos.get(0).y), -1);
            bfs(start);

            System.out.println(res);
        }
    }

    static void bfs(Laser st) {
        Queue<Laser> q = new LinkedList<>();
        q.add(st);
        visited[st.l.x][st.l.y] = true;

        while (!q.isEmpty()) {
            Laser cur = q.poll();

            if (cur.l.x == cPos.get(1).x && cur.l.y == cPos.get(1).y) {
                res = cur.cnt;
                return;
            }

            for (int dir = 0; dir < 4; dir++) {
                Laser nxt = new Laser(new Pos(cur.l.x, cur.l.y), cur.cnt);

                while (true) {
                    nxt.l.x += di[dir];
                    nxt.l.y += dj[dir];

                    if (nxt.l.x < 0 || nxt.l.y < 0 || nxt.l.x >= H || nxt.l.y >= W) break;
                    if (map[nxt.l.x][nxt.l.y] == '*') break;

                    if (!visited[nxt.l.x][nxt.l.y]) {
                        nxt.cnt = cur.cnt + 1;
                        visited[nxt.l.x][nxt.l.y] = true;
                        q.add(new Laser(new Pos(nxt.l.x, nxt.l.y), nxt.cnt));
                    }
                }
            }
        }
    }
}

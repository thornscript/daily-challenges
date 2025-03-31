import java.io.*;
import java.util.*;

public class 맥주_마시면서_걸어가기 {

    static int n;
    static int[][] points;

    static class State {
        int i, beer;

        State(int i, int beer) {
            this.i = i;
            this.beer = beer;
        }
    }

    static boolean solve() {
        int[] d = new int[n + 2];

        Queue<State> q = new LinkedList<>();
        q.add(new State(0, 20));

        while (!q.isEmpty()) {
            State cur = q.poll();

            if (cur.beer < d[cur.i]) continue;
            if (points[cur.i][0] == points[n + 1][0] && points[cur.i][1] == points[n + 1][1]) return true;

            for (int i = 1; i < n + 2; ++i) {
                int diff = Math.abs(points[cur.i][0] - points[i][0]) + Math.abs(points[cur.i][1] - points[i][1]);
                int requiredBeer = (diff + 49) / 50;
                if (diff == 0 || cur.beer < requiredBeer) continue;
                if (cur.beer > d[i]) {
                    d[i] = cur.beer;
                    q.add(new State(i, 20));
                }
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int t = Integer.parseInt(br.readLine());
            while (t-- > 0) {
                n = Integer.parseInt(br.readLine());

                points = new int[n + 2][2];
                for (int i = 0; i < n + 2; ++i) { // 집, 편의점, 펜타포트
                    StringTokenizer st = new StringTokenizer(br.readLine());

                    points[i][0] = Integer.parseInt(st.nextToken());
                    points[i][1] = Integer.parseInt(st.nextToken());
                }

                System.out.println(solve() ? "happy" : "sad");
            }
        }
    }
}

import java.util.*;

public class 월요병 {
    
    static int N, M;
    static final long INF = Long.MAX_VALUE;
    static long[][] village, dist;
    static int[] dx = {-1, -1, -1, 1, 1, 1, 0, 0};
    static int[] dy = {-1, 0, 1, -1, 0, 1, -1, 1};

    static class Node implements Comparable<Node> {
        int x, y;
        long cost;

        public Node(int x, int y, long cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node other) {
            return Long.compare(this.cost, other.cost);
        }
    }

    public static void dijkstra() {
        dist = new long[N][M];
        for (int i = 0; i < N; i++)
            Arrays.fill(dist[i], INF);
        
        PriorityQueue<Node> pq = new PriorityQueue<>();
        
        for (int i = 0; i < M; i++) {
            if (village[0][i] == INF) continue;
            dist[0][i] = village[0][i];
            pq.offer(new Node(i, 0, village[0][i]));
        }
        
        for (int i = 0; i < N; i++) {
            if (village[i][M - 1] == INF) continue;
            if (village[i][M - 1] < dist[i][M - 1]) {
                dist[i][M - 1] = village[i][M - 1];
                pq.offer(new Node(M - 1, i, village[i][M - 1]));
            }
        }
        
        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            int x = cur.x;
            int y = cur.y;
            long cost = cur.cost;
            
            if (dist[y][x] < cost) continue;
            
            for (int i = 0; i < 8; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (nx < 0 || nx >= M || ny < 0 || ny >= N) continue;
                if (village[ny][nx] == INF) continue;
                long newCost = cost + village[ny][nx];
                if (dist[ny][nx] > newCost) {
                    dist[ny][nx] = newCost;
                    pq.offer(new Node(nx, ny, newCost));
                }
            }
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            N = sc.nextInt();
            M = sc.nextInt();
            village = new long[N][M];
            
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    long input = sc.nextLong();
                    if (input == -2) input = 0;
                    if (input == -1) input = INF;
                    village[i][j] = input;
                }
            }
            
            dijkstra();
            
            long ans = INF;
            for (int i = 0; i < N; i++)
                ans = Math.min(ans, dist[i][0]);
            for (int i = 0; i < M; i++)
                ans = Math.min(ans, dist[N - 1][i]);
            
            if (ans == INF) System.out.println(-1);
            else System.out.println(ans);
        }
    }
}

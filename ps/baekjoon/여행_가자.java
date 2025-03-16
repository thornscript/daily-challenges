import java.io.*;
import java.util.*;

public class 여행_가자 {
    static int[][] adj;
    static int[] parents;

    static int find(int x) {
        if (x == parents[x]) return x;
        return parents[x] = find(parents[x]);
    }

    static void merge(int x, int y) {
        x = find(x);
        y = find(y);
        parents[y] = x;
    }
 
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int N = Integer.parseInt(br.readLine());
            int M = Integer.parseInt(br.readLine());

            parents = new int[N + 1];
            for (int i = 1; i < parents.length; ++i)
                parents[i] = i;

            adj = new int[N + 1][N + 1];
            for (int i = 1; i <= N; ++i) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 1; j <= N; ++j) {
                    adj[i][j] = Integer.parseInt(st.nextToken());
                    if (adj[i][j] == 1) {
                        merge(i, j);
                    }
                }
            }

            StringTokenizer st = new StringTokenizer(br.readLine());
            int[] plan = new int[M];
            for (int i = 0; i < M; i++) {
                plan[i] = Integer.parseInt(st.nextToken());
            }
            
            int group = find(plan[0]);
            for (int i = 1; i < M; ++i) {
                if (find(plan[i]) != group) {
                    System.out.println("NO");
                    return;
                }
            }
            System.out.println("YES");
        }
    }
 }

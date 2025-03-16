import java.io.*;
import java.util.*;

public class 여행_가자 {
    static int[] parents;

    static int find(int x) {
        if (x == parents[x]) return x;
        return parents[x] = find(parents[x]);
    }

    static void union(int x, int y) {
        x = find(x);
        y = find(y);
        
        if (x == y) return;
        parents[y] = x;
    }
 
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {        
            int N = Integer.parseInt(br.readLine());
            int M = Integer.parseInt(br.readLine());

            parents = new int[N + 1];
            for (int i = 1; i <= N; i++) parents[i] = i;

            for (int i = 1; i <= N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 1; j <= N; j++)
                    if (Integer.parseInt(st.nextToken()) == 1)
                        union(i, j);
            }

            StringTokenizer st = new StringTokenizer(br.readLine());
            int firstCity = Integer.parseInt(st.nextToken());
            int rootGroup = find(firstCity);
            
            for (int i = 1; i < M; i++) {
                int nextCity = Integer.parseInt(st.nextToken());
                if (find(nextCity) != rootGroup) {
                    System.out.println("NO");
                    return;
                }
            }
            
            System.out.println("YES");
        }  
    }
}

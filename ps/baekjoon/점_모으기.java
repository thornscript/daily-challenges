import java.io.*;
import java.util.*;

public class 점_모으기 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            int[] rows = new int[M];
            int[] cols = new int[M];

            for (int i = 0; i < M; ++i) {
                st = new StringTokenizer(br.readLine());

                rows[i] = Integer.parseInt(st.nextToken());
                cols[i] = Integer.parseInt(st.nextToken());
            }

            Arrays.sort(rows); // O(mlogm)
            Arrays.sort(cols);

            int totalDist = 0;
            int midX = rows[M / 2], midY = cols[M / 2];
            for (int i = 0; i < M; ++i)
                totalDist += Math.abs(rows[i] - midX) + Math.abs(cols[i] - midY);
            
            System.out.println(totalDist);
        }
    }
}

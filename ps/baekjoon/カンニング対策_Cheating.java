import java.util.*;
import java.io.*;

public class カンニング対策_Cheating {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int n = Integer.parseInt(st.nextToken()); // 제작한 감시 장치의 개수
            int m = Integer.parseInt(st.nextToken()); // 주의 대상 수험자의 수
            
            long[] xs = new long[m];
            long[] ys = new long[m];
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());

                xs[i] = Long.parseLong(st.nextToken());
                ys[i] = Long.parseLong(st.nextToken());
            }
            
            Arrays.sort(xs);
            Arrays.sort(ys);
            
            long lo = 0, hi = 1_000_000_000;
            long ans = hi;
            while (lo <= hi) {
                long mid = (lo + hi) / 2;
                int sensorsForX = countSensorsNeeded(xs, mid);
                int sensorsForY = countSensorsNeeded(ys, mid);
                if (sensorsForX + sensorsForY <= n) {
                    ans = mid;
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }

            System.out.println(ans);
        }
    }

    private static int countSensorsNeeded(long[] coord, long d) {
        int count = 0;
        int idx = 0;
        int m = coord.length;

        while (idx < m) {
            count++; 
            long coverEnd = coord[idx] + d;
            while (idx < m && coord[idx] <= coverEnd) {
                idx++;
            }
        }
        return count;
    }
}

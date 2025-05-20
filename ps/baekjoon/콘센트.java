import java.util.*;
import java.io.*;

public class 콘센트 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            int[] devices = new int[N];
            for (int i = 0; i < N; ++i)
                devices[i] =  Integer.parseInt(st.nextToken());
            Arrays.sort(devices);

            int ans = 0;
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            for (int i = N - 1; i >= 0; --i) {
                if (pq.size() < M) {
                    pq.add(devices[i]);
                    ans = Math.max(ans, devices[i]);
                } else {
                    int finish = pq.poll() + devices[i];
                    pq.add(finish);
                    ans = Math.max(ans, finish);
                }
            }
            System.out.println(ans);
        }        
    }
}

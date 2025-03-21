import java.util.*;
import java.io.*;

public class 멀티버스 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            
            int M = Integer.parseInt(st.nextToken());
            int N = Integer.parseInt(st.nextToken());
            int[][] arr = new int[M][N];
            List<List<Integer>> lst = new ArrayList<>();

            for (int i = 0; i < M; ++i) {
                st = new StringTokenizer(br.readLine());
                Set<Integer> set = new HashSet<>();
                for (int j = 0; j < N; ++j) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                    set.add(arr[i][j]);
                }
                lst.add(new ArrayList<>(set));
                Collections.sort(lst.get(i));
            }

            for (int i = 0; i < M; ++i)
                for (int j = 0; j < N; ++j)
                    arr[i][j] = Collections.binarySearch(lst.get(i), arr[i][j]);
            
            int cnt = 0;
            for (int i = 0; i < M; ++i)
                for (int j = i + 1; j < M; ++j)
                    if (Arrays.equals(arr[i], arr[j]))
                        cnt++;
            
            System.out.println(cnt);
        }
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class 공유기_설치 {
    static int N, C;

    static boolean isPossible(List<Integer> lst, int mid) {
        int c = 1, prev = lst.get(0);
        for (int i = 1; i < lst.size(); ++i) {
            if (lst.get(i) - prev >= mid) {
                prev = lst.get(i);
                c++;
            }
        }
        return c >= C;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());

            List<Integer> lst = new ArrayList<>();
            for (int i = 0; i < N; ++i)
                lst.add(Integer.parseInt(br.readLine()));
            Collections.sort(lst);

            int left = 1, right = (int) 1e9, ans = 0;
            while (left <= right) {
                int mid = (left + right) / 2;
                
                if (isPossible(lst, mid)) {
                    left = mid + 1;
                    ans = Math.max(ans, mid);
                } else {
                    right = mid - 1;
                }
            }
            System.out.println(ans);
        }
    }
}

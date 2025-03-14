import java.util.*;
import java.io.*;

class 도둑 {

    static int solve(int N, int M, int K, int[] houses) {
        int left = 0, right = 0;
        int cur = 0, ans = 0;
        while (left < N) {
            if (cur + houses[right] >= K || (right - left + 1) > M) {
                cur -= houses[left++];
            } else {
                if (right - left + 1 == M) ans++;
                cur += houses[right++];
            }
        }
        return (M == N && N == ans) ? 1 : ans;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int T = Integer.parseInt(br.readLine());
            while (T-- > 0) {
                StringTokenizer st = new StringTokenizer(br.readLine());

                int N = Integer.parseInt(st.nextToken());
                int M = Integer.parseInt(st.nextToken());
                int K = Integer.parseInt(st.nextToken());

                // 환형 문제를 쉽게 풀기 위해서 공간을 두 배로 늘림
                int[] houses = new int[N * 2];
                st = new StringTokenizer(br.readLine());
                for (int i = 0; i < N; ++i)
                    houses[i] = houses[N + i] = Integer.parseInt(st.nextToken());
                
                System.out.println(solve(N, M, K, houses));
            }
        }
    }
}

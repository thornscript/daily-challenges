import java.util.*;

public class 같이_눈사람_만들래 {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int N = sc.nextInt();

            int[] snow = new int[N];
            for (int i = 0; i < N; ++i)
                snow[i] = sc.nextInt();
            
            Arrays.sort(snow);
            
            int ans = (int) 1e9;
            for (int i = 0; i < N; ++i) {
                for (int j = i + 3; j < N; ++j) {
                    int one = snow[i] + snow[j];
                    int l = i + 1;
                    int r = j - 1;
                    while (l <= r) {
                        int two = snow[l] + snow[r];
                        ans = Math.min(ans, Math.abs(one - two));
                        if (one - two <= 0) r--;
                        else l++;
                    }
                }
            }
            System.out.println(ans);
        }
    }
}

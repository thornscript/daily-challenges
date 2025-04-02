import java.util.*;
import java.io.*;

public class Text_Messaging_Outrage_Small {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int t = Integer.parseInt(br.readLine());

            for (int i = 1; i <= t; ++i) {
                StringTokenizer st = new StringTokenizer(br.readLine());

                int P = Integer.parseInt(st.nextToken());
                int K = Integer.parseInt(st.nextToken());
                int L = Integer.parseInt(st.nextToken());

                int[] freq = new int[L];
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < L; ++j)
                    freq[j] = Integer.parseInt(st.nextToken());

                Arrays.sort(freq);
                int ans = 0;
                for (int j = L - 1; j >= 0; j--)
                    ans += freq[j] * ((L - 1 - j) / K + 1);
                System.out.println(String.format("Case #%d: %d", i, ans));
            }
        }
    }
}

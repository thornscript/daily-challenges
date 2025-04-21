import java.io.*;
import java.util.*;

public class 전구 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int N = Integer.parseInt(br.readLine());

            StringTokenizer st = new StringTokenizer(br.readLine());
            int[] switches = new int[N];
            for (int i = 0; i < N; ++i) switches[i] = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            int[] bulbs = new int[N + 1];
            for (int i = 0; i < N; ++i) bulbs[Integer.parseInt(st.nextToken())] = i;

            int[] tail = new int[N];
            int[] parent = new int[N];
            int length = 0;

            for (int i = 0; i < N; ++i) {
                int x = bulbs[switches[i]];
                int l = 0, r = length;
                while (l < r) {
                    int mid = (l + r) / 2;
                    if (bulbs[switches[tail[mid]]] < x) l = mid + 1;
                    else r = mid;
                }
                int pos = l;
                parent[i] = (pos > 0 ? tail[pos - 1] : -1);
                tail[pos] = i;
                if (pos == length) length++;
            }

            int[] ans = new int[length];
            int idx = tail[length - 1];
            for (int i = length - 1; i >= 0; --i) {
                ans[i] = idx;
                idx = parent[idx];
            }

            int[] ret = new int[length];
            for (int i = 0; i < length; ++i)
                ret[i] = switches[ans[i]];
            Arrays.sort(ret);

            System.out.println(length);
            for (int i = 0; i < length; ++i)
                System.out.print(ret[i] + " ");
        }
    }
}

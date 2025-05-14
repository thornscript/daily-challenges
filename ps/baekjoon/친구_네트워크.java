import java.io.*;
import java.util.*;

public class 친구_네트워크 {

    static Map<String, String> parents;
    static Map<String, Integer> cnt;

    static String find(String a) {
        String p = parents.get(a);
        if (!a.equals(p)) {
            p = find(p);
            parents.put(a, p);
        }
        return p;
    }

    static int merge(String a, String b) {
        String ap = find(a);
        String bp = find(b);
        int sum = cnt.getOrDefault(ap, 1) + cnt.getOrDefault(bp, 1);
        cnt.put(ap, 0);
        cnt.put(bp, sum);
        parents.put(ap, bp);
        return sum;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int T = Integer.parseInt(br.readLine());

            while (T-- > 0) {
                int F = Integer.parseInt(br.readLine()); // <= 100,000

                parents = new HashMap<>();
                cnt = new HashMap<>();

                for (int i = 0; i < F; ++i) {
                    StringTokenizer st = new StringTokenizer(br.readLine());

                    String A = st.nextToken();
                    String B = st.nextToken();

                    if (!parents.containsKey(A)) parents.put(A, A);
                    if (!parents.containsKey(B)) parents.put(B, B);

                    if (!find(A).equals(find(B))) System.out.println(merge(A, B));
                    else System.out.println(cnt.get(find(A)));
                }
            }
        }
    }
}

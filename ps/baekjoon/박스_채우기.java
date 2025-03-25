import java.util.*;

public class 박스_채우기 {

    static int length, width, height, N, ans;
    static int[] cube = new int[21];

    static void fail() {
        System.out.println("-1");
        System.exit(0);
    }

    static void solve(int length, int width, int height) {
        if (length == 0 || width == 0 || height == 0)
            return;
        
        int minEdge = Math.min(Math.min(length, width), height);
        int idx = (int) (Math.log(minEdge) / Math.log(2));

        boolean flag = false;
        for (int i = idx; i >= 0; --i) {
            if (cube[i] > 0) {
                idx = i;
                flag = true;
                break;
            }
        }

        if (!flag) {
            System.out.println("-1");
            System.exit(0);
        }

        int t = (int) Math.pow(2, idx);
        ans++;
        cube[idx]--;

        solve(length, width, height - t);
        solve(length, width - t, t);
        solve(length - t, t, t);
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());

            length = Integer.parseInt(st.nextToken());
            width = Integer.parseInt(st.nextToken());
            height = Integer.parseInt(st.nextToken());

            N = Integer.parseInt(sc.nextLine());
            for (int i = 0; i < N; ++i) {
                st = new StringTokenizer(sc.nextLine());

                int A = Integer.parseInt(st.nextToken());
                int B = Integer.parseInt(st.nextToken());

                cube[A] = B;
            }

            solve(length, width, height);
            System.out.println(ans);
        }
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class 탑 {

    static class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int N = Integer.parseInt(br.readLine());

            Stack<Pair> s = new Stack<>();
            StringTokenizer st = new StringTokenizer(br.readLine());
            
            int[] ans = new int[N + 1];
            for (int i = 1; i <= N; ++i) {
                int height = Integer.parseInt(st.nextToken());

                while (!s.isEmpty() && s.peek().y < height)
                    s.pop();
                
                ans[i] = s.isEmpty() ? 0 : s.peek().x;

                s.add(new Pair(i, height));
            }

            for (int i = 1; i <= N; ++i)
                System.out.print(ans[i] + " ");
        }
    }
}

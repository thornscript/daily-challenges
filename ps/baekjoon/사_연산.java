import java.util.*;

public class 사_연산 {

    static char[] ops = {'*', '+', '-', '/'};

    static long calc(char op, long a, long b) {
        if (op == '*') return a * b;
        else if (op == '+') return a + b;
        else if (op == '-') return a - b;
        else return a / b;
    }

    static class State {
        long cur;
        String path;

        State(long cur, String path) {
            this.cur = cur;
            this.path = path;
        }
    }

    static void solve(Long S, Long T) {
        Map<Long, Boolean> visited = new HashMap<>();

        Queue<State> q = new LinkedList<>();
        q.add(new State(S, ""));
        visited.put(S, true);

        while (!q.isEmpty()) {
            State cur = q.poll();

            for (int i = 0; i < 4; ++i) {
                if (i == 3 && cur.cur == 0) continue;
                long ret = calc(ops[i], cur.cur, cur.cur);
                if (!visited.containsKey(ret)) {
                    if (ret == T) {
                        System.out.println(cur.path + ops[i]);
                        System.exit(0);
                    }
                    visited.put(ret, true);
                    q.add(new State(ret, cur.path + ops[i]));
                }
            }
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            long S = sc.nextLong();
            long T = sc.nextLong();

            if (S == T) {
                System.out.println(0);
                return;
            }

            solve(S, T);
            System.out.println(-1);
        }
    }
}

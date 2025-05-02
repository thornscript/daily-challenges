import java.util.*;
import java.io.*;

public class 컵라면 {

    static class Task {
        int deadline, amount;

        Task(int deadline, int amount) {
            this.deadline = deadline;
            this.amount = amount;
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int N = Integer.parseInt(br.readLine());

            PriorityQueue<Task> pq = new PriorityQueue<>((t, o) -> {
                if (t.deadline == o.deadline) return Integer.compare(o.amount, t.amount);
                return Integer.compare(t.deadline, o.deadline);
            });

            for (int i = 0; i < N; ++i) {
                StringTokenizer st = new StringTokenizer(br.readLine());

                int deadline = Integer.parseInt(st.nextToken());
                int cup = Integer.parseInt(st.nextToken());

                pq.add(new Task(deadline, cup));
            }

            long ans = 0, cur = 1;
            PriorityQueue<Integer> aq = new PriorityQueue<>((t, o) -> Integer.compare(t, o));
            while (!pq.isEmpty()) {
                Task t = pq.poll();

                if (cur <= t.deadline) {
                    aq.add(t.amount);
                    cur++;
                } else if (aq.peek() < t.amount) {
                    aq.poll();
                    aq.add(t.amount);
                }
            }

            while (!aq.isEmpty())
                ans += aq.poll();
            System.out.println(ans);
        }
    }
}

import java.io.*;
import java.util.*;

public class 싸지방에_간_준하 {

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


            List<Pair> l = new ArrayList<>();
            for (int i = 0; i < N; ++i) {
                StringTokenizer st = new StringTokenizer(br.readLine());

                int P = Integer.parseInt(st.nextToken());
                int Q = Integer.parseInt(st.nextToken());

                l.add(new Pair(P, Q));
            }
            l.sort((a, b) -> Integer.compare(a.x, b.x)); // 기다리지 않아야 함

            int no = 0;
            List<Integer> cnt = new ArrayList<>();
            PriorityQueue<Pair> pq = new PriorityQueue<>(
                Comparator.<Pair>comparingInt(r -> r.y)
                .thenComparingInt(r -> r.x)
            ); // {방 번호, 마지막 이용 시간}
            PriorityQueue<Integer> available = new PriorityQueue<>();

            for (Pair p : l) {
                while (!pq.isEmpty() && pq.peek().y <= p.x) {
                    available.add(pq.poll().x);
                }

                if (available.isEmpty()) {
                    pq.add(new Pair(no++, p.y));
                    cnt.add(1);
                } else {
                    int seat = available.poll();
                    cnt.set(seat, cnt.get(seat) + 1);
                    pq.add(new Pair(seat, p.y));
                }
            }

            System.out.println(cnt.size());
            for (int i = 0; i < cnt.size(); ++i)
                System.out.print(cnt.get(i) + " ");
        }
    }
}

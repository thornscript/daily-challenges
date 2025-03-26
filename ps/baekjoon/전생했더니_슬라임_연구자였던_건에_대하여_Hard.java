import java.io.*;
import java.util.*;

public class 전생했더니_슬라임_연구자였던_건에_대하여_Hard {

    static final long MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
                int T = Integer.parseInt(br.readLine().trim());

                for (int t = 0; t < T; t++) {
                    int N = Integer.parseInt(br.readLine().trim());
                    StringTokenizer st = new StringTokenizer(br.readLine());

                    PriorityQueue<Long> pq = new PriorityQueue<>();
                    for (int i = 0; i < N; i++) {
                        long energy = Long.parseLong(st.nextToken());
                        pq.offer(energy);
                    }

                    long cost = 1;
                    while (pq.size() > 1) {
                        long first = pq.poll();
                        long second = pq.poll();
                        long newEnergy = first * second;
                        long currentCost = (first * second) % MOD;
                        cost = (cost * currentCost) % MOD;
                        pq.offer(newEnergy);
                    }
                    bw.write(cost + "\n");
                }
            }
        }
    }
}

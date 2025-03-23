import java.util.*;

public class 가장높은탑쌓기 {

    static int N;
    static int[] dp;
    static int[] prev;
    static List<Block> blocks;

    static class Block {
        int no, area, height, weight;

        Block(int no, int area, int height, int weight) {
            this.no = no;
            this.area = area;
            this.height = height;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            N = Integer.parseInt(sc.nextLine()); // 최대 100

            blocks = new ArrayList<>();
            for (int i = 1; i <= N; ++i) {
                StringTokenizer st = new StringTokenizer(sc.nextLine());

                int area = Integer.parseInt(st.nextToken());
                int height = Integer.parseInt(st.nextToken());
                int weight = Integer.parseInt(st.nextToken());

                blocks.add(new Block(i, area, height, weight));
            }

            blocks.sort((a, b) -> {
                if (a.area == b.area) return a.weight - b.weight;
                return a.area - b.area;
            });

            dp = new int[N];
            prev = new int[N];
            Arrays.fill(prev, -1);

            int maxHeight = 0;
            int maxIndex = 0;

            for (int i = 0; i < N; i++) {
                dp[i] = blocks.get(i).height;

                for (int j = 0; j < i; j++) {
                    if (blocks.get(j).area < blocks.get(i).area &&
                        blocks.get(j).weight < blocks.get(i).weight) {
                        if (dp[j] + blocks.get(i).height > dp[i]) {
                            dp[i] = dp[j] + blocks.get(i).height;
                            prev[i] = j;
                        }
                    }
                }

                if (dp[i] > maxHeight) {
                    maxHeight = dp[i];
                    maxIndex = i;
                }
            }

            List<Integer> path = new ArrayList<>();
            while (maxIndex != -1) {
                path.add(blocks.get(maxIndex).no);
                maxIndex = prev[maxIndex];
            }

            System.out.println(path.size());
            for (int i = path.size() - 1; i >= 0; i--)
                System.out.println(path.get(i));
        }
    }
}

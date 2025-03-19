import java.util.*;
import java.io.*;

public class 소용돌이_예쁘게_출력하기 {
    static int[] di = {1, 0, -1, 0}, dj = {0, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            
            int r1 = Integer.parseInt(st.nextToken());
            int c1 = Integer.parseInt(st.nextToken());
            int r2 = Integer.parseInt(st.nextToken());
            int c2 = Integer.parseInt(st.nextToken());

            int m = Math.max(Math.max(Math.abs(r1), Math.abs(r2)), Math.max(Math.abs(c1), Math.abs(c2)));
            int l = m * 2 + 1;
            r1 += m;
            r2 += m;
            c1 += m;
            c2 += m;

            int[][] v = new int[r2 - r1 + 1][c2 - c1 + 1];
            List<Integer> p = new ArrayList<>();
            p.add(1);

            int cx = l / 2, cy = l / 2, num = 1, dir = 0;

            while (num <= l * l) {
                for (int i = 0; i < p.get(p.size() - 1); ++i) {
                    if (cy >= r1 && cy <= r2 && cx >= c1 && cx <= c2)
                        v[cy - r1][cx - c1] = num;
                    num++;
                    cy += dj[dir];
                    cx += di[dir];
                }
                dir = (dir + 1) % 4;
                p.add(p.size() % 2 == 1 ? p.get(p.size() - 1) : p.get(p.size() - 1) + 1);
            }

            int pad = digitCount(Collections.max(Arrays.asList(
                    v[0][0], v[0][v[0].length - 1], v[v.length - 1][0], v[v.length - 1][v[0].length - 1])));

            for (int[] row : v) {
                for (int val : row)
                    System.out.print(String.format("%" + pad + "d ", val));
                System.out.println();
            }
        }
    }

    static int digitCount(int x) {
        return (int) Math.floor(Math.log10(x)) + 1;
    }
}

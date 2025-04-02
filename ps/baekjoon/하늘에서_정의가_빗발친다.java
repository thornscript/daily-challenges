import java.io.*;
import java.util.*;

public class 하늘에서_정의가_빗발친다 {
    
    static class Robot {
        int idx;
        long d;
        long v2;
        
        Robot(int idx, long d, long v2) {
            this.idx = idx;
            this.d = d;
            this.v2 = v2;
        }
    }
    
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)))) {
                int N = Integer.parseInt(br.readLine());
                Robot[] robots = new Robot[N];
                
                for (int i = 0; i < N; i++) {
                    StringTokenizer st = new StringTokenizer(br.readLine());
                    int x = Integer.parseInt(st.nextToken());
                    int y = Integer.parseInt(st.nextToken());
                    int v = Integer.parseInt(st.nextToken());
                    long d = (long)x * x + (long)y * y;
                    long v2 = (long)v * v;
                    robots[i] = new Robot(i + 1, d, v2);
                }
                
                Arrays.sort(robots, new Comparator<Robot>() {
                    public int compare(Robot r1, Robot r2) {
                        long lhs = r1.d * r2.v2;
                        long rhs = r2.d * r1.v2;
                        if (lhs < rhs) return -1;
                        else if (lhs > rhs) return 1;
                        else return Integer.compare(r1.idx, r2.idx);
                    }
                });
                
                for (Robot r : robots)
                    out.println(r.idx);
            }
        }
    }
}

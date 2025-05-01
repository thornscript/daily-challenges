import java.io.*;
import java.util.*;

public class 강의실 {

    static class Lecture implements Comparable<Lecture> {
        int no;
        int startTime, endTime;

        Lecture(int no, int startTime, int endTime) {
            this.no = no;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        @Override
        public int compareTo(Lecture o) {
            return Integer.compare(this.startTime, o.startTime);
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int N = Integer.parseInt(br.readLine());

            List<Lecture> lectures = new ArrayList<>();
            for (int i = 0; i < N; ++i) {
                StringTokenizer st = new StringTokenizer(br.readLine());

                int no = Integer.parseInt(st.nextToken());
                int startTime = Integer.parseInt(st.nextToken());
                int endTime = Integer.parseInt(st.nextToken());

                lectures.add(new Lecture(no, startTime, endTime));
            }

            Collections.sort(lectures); // 강의 시작 시간으로 오름차순 정렬
 
            PriorityQueue<Lecture> pq = new PriorityQueue<>((t, o) -> Integer.compare(t.endTime, o.endTime));

            for (int i = 0; i < lectures.size(); ++i) {
                if (!pq.isEmpty()) {
                    Lecture l = pq.poll();
                    if (l.endTime <= lectures.get(i).startTime) {
                        l.startTime = lectures.get(i).startTime;
                        l.endTime = lectures.get(i).endTime;
                    } else pq.add(lectures.get(i));
                    pq.add(l);
                } else pq.add(lectures.get(i));
            }

            System.out.println(pq.size());
        }
    }
}

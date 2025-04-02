import java.util.*;

public class 궁합_쌍_찾기 {

    static class Pair implements Comparable<Pair> {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public int compareTo(Pair o) {
            if (this.x == o.x) return Integer.compare(this.y, o.y);
            return Integer.compare(this.x, o.x);
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair)) return false;
            Pair pair = (Pair) o;
            return this.x == pair.x && this.y == pair.y;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            List<Integer> lst = new ArrayList<>();
            while (st.hasMoreTokens()) {
                lst.add(Integer.parseInt(st.nextToken()));
            }
            
            Collections.sort(lst);
            int target = sc.nextInt();
            
            Set<Pair> pairs = new TreeSet<>();
            for (int i = 0; i < lst.size(); ++i)
                for (int j = i + 1; j < lst.size(); ++j)
                    if (lst.get(i) + lst.get(j) == target)
                        pairs.add(new Pair(lst.get(i), lst.get(j)));
            
            for (Pair p : pairs)
                System.out.println(p.x + " " + p.y);
            System.out.println(pairs.size());
        }
    }
}

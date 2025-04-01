import java.io.*;
import java.util.*;

public class Car_Trouble {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        Map<Integer, List<Integer>> forward = new HashMap<>();
        Map<Integer, List<Integer>> reverse = new HashMap<>();
        
        List<Integer> inputOrder = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int id = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            
            forward.putIfAbsent(id, new ArrayList<>());
            reverse.putIfAbsent(id, new ArrayList<>());
            
            if (id != 0)
                inputOrder.add(id);
            
            for (int j = 0; j < m; j++) {
                int next = Integer.parseInt(st.nextToken());
                forward.get(id).add(next);
                reverse.putIfAbsent(next, new ArrayList<>());
                reverse.get(next).add(id);
            }
        }
        
        Set<Integer> reachable = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        reachable.add(0);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            if (forward.containsKey(cur)) {
                for (int next : forward.get(cur)) {
                    if (!reachable.contains(next)) {
                        reachable.add(next);
                        queue.add(next);
                    }
                }
            }
        }
        
        Set<Integer> canReachZero = new HashSet<>();
        Queue<Integer> queueRev = new LinkedList<>();
        queueRev.add(0);
        canReachZero.add(0);
        while (!queueRev.isEmpty()) {
            int cur = queueRev.poll();
            if (reverse.containsKey(cur)) {
                for (int prev : reverse.get(cur)) {
                    if (!canReachZero.contains(prev)) {
                        canReachZero.add(prev);
                        queueRev.add(prev);
                    }
                }
            }
        }
        
        boolean problemFound = false;
        for (int id : inputOrder) {
            if (!canReachZero.contains(id)) {
                System.out.println("TRAPPED " + id);
                problemFound = true;
            }
        }
        for (int id : inputOrder) {
            if (!reachable.contains(id)) {
                System.out.println("UNREACHABLE " + id);
                problemFound = true;
            }
        }
        
        if (!problemFound) {
            System.out.println("NO PROBLEMS");
        }
    }
}

import java.util.*;

public class Magicka_Small {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int T = sc.nextInt();
            
            for (int t = 1; t <= T; t++) {
                int C = sc.nextInt();
                Map<String, Character> combine = new HashMap<>();
                for (int i = 0; i < C; i++) {
                    String comb = sc.next();
                    char a = comb.charAt(0);
                    char b = comb.charAt(1);
                    char res = comb.charAt(2);
                    combine.put("" + a + b, res);
                    combine.put("" + b + a, res);
                }
                
                int D = sc.nextInt();
                Map<Character, Set<Character>> oppose = new HashMap<>();
                for (int i = 0; i < D; i++) {
                    String opp = sc.next();
                    char a = opp.charAt(0);
                    char b = opp.charAt(1);
                    oppose.computeIfAbsent(a, k -> new HashSet<>()).add(b);
                    oppose.computeIfAbsent(b, k -> new HashSet<>()).add(a);
                }
                
                int N = sc.nextInt();
                String invocations = sc.next();
                
                List<Character> elementList = new ArrayList<>();
                
                for (int i = 0; i < invocations.length(); i++) {
                    char cur = invocations.charAt(i);
                    if (!elementList.isEmpty()) {
                        char last = elementList.get(elementList.size() - 1);
                        String key = "" + last + cur;
                        if (combine.containsKey(key)) {
                            char newElem = combine.get(key);
                            elementList.remove(elementList.size() - 1);
                            elementList.add(newElem);
                            continue;
                        }
                    }
                    
                    elementList.add(cur);
                    if (oppose.containsKey(cur)) {
                        Set<Character> opposedSet = oppose.get(cur);
                        for (char e : elementList) {
                            if (opposedSet.contains(e)) {
                                elementList.clear();
                                break;
                            }
                        }
                    }
                }
                
                StringBuilder sb = new StringBuilder();
                sb.append("Case #").append(t).append(": [");
                for (int i = 0; i < elementList.size(); i++) {
                    sb.append(elementList.get(i));
                    if (i != elementList.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("]");
                System.out.println(sb.toString());
            }
        }
    }
}

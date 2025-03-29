import java.io.*;
import java.util.*;

public class 전화번호_목록 {

    static class TrieNode {
        private Map<Character, TrieNode> children;
        private boolean isEndOfWord;

        public TrieNode() {
            this.children = new HashMap<>();
            this.isEndOfWord = false;
        }

        public boolean insert(String str, int index) {
            if (str.length() == index) {
                this.isEndOfWord = true;
                return children.isEmpty();
            }
            Character ch = str.charAt(index);
            if (!children.containsKey(ch)) children.put(ch, new TrieNode());
            else if (children.get(ch).isEndOfWord) return false;
            return children.get(ch).insert(str, index + 1);
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int t = Integer.parseInt(br.readLine());

            while (t-- > 0) {
                int n = Integer.parseInt(br.readLine());

                TrieNode trie = new TrieNode();
                boolean flag = true;
                for (int i = 0; i < n; ++i) {
                    String number = br.readLine();
                    if (!trie.insert(number, 0))
                        flag = false;
                }
                System.out.println(flag ? "YES" : "NO");
            }
        }
    }
}

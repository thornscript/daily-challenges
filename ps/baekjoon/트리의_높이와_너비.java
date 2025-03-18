import java.util.*;
import java.io.*;

public class 트리의_높이와_너비 {
    static HashMap<Integer, TreeNode> tree;
    static final TreeNode EMPTY_NODE = new TreeNode(-1);
    static int N, cnt = 1;
    static int[] parent;
    static int[][] diffWidth;

    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    static void inOrder(TreeNode x, int depth) { // 왼 루 오
        if (x.left != EMPTY_NODE) inOrder(x.left, depth + 1);
        diffWidth[depth][0] = Math.min(diffWidth[depth][0], cnt);
        diffWidth[depth][1] = Math.max(diffWidth[depth][1], cnt);
        cnt++;
        if (x.right != EMPTY_NODE) inOrder(x.right, depth + 1);
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            N = Integer.parseInt(br.readLine());
            diffWidth = new int[10001][2];
            parent = new int[10001];
            tree = new HashMap<>();
            for (int i = 1; i < 10001; ++i) {
                diffWidth[i][0] = Integer.MAX_VALUE; // min
                diffWidth[i][1] = Integer.MIN_VALUE; // max
            }
            for (int i = 1; i <= N; ++i) {
                parent[i] = i;
                tree.put(i, new TreeNode(i));
            }
            
            for (int i = 0; i < N; ++i) {
                StringTokenizer st = new StringTokenizer(br.readLine());

                int nodeNum = Integer.parseInt(st.nextToken());
                int L = Integer.parseInt(st.nextToken());
                int R = Integer.parseInt(st.nextToken());
                if (L != -1) parent[L] = nodeNum;
                if (R != -1) parent[R] = nodeNum;

                tree.get(nodeNum).left = L == -1 ? EMPTY_NODE : tree.get(L);
                tree.get(nodeNum).right = R == -1 ? EMPTY_NODE : tree.get(R);
            }

            for (int i = 1; i <= N; ++i) { // 루트 노드
                if (parent[i] == i) {
                    inOrder(tree.get(i), 1);
                    break;
                }
            }

            int ans = -1, temp = Integer.MIN_VALUE;
            for (int i = 1; i < 10001; ++i) {
                if (diffWidth[i][0] == Integer.MAX_VALUE || diffWidth[i][1] == Integer.MIN_VALUE) break;
                if (diffWidth[i][1] - diffWidth[i][0] > temp) {
                    temp = diffWidth[i][1] - diffWidth[i][0];
                    ans = i;
                }
            }
            System.out.println(String.format("%d %d", ans, temp + 1));
        }
    }
}

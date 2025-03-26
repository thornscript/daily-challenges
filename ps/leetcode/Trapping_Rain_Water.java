public class Trapping_Rain_Water {

    public int trap(int[] height) {
        int N = height.length;

        int[] leftHeight = new int[N];
        int[] rightHeight = new int[N];

        leftHeight[0] = height[0];
        for (int i = 1; i < N; ++i)
            leftHeight[i] = Math.max(leftHeight[i - 1], height[i]);
        
        rightHeight[N - 1] = height[N - 1];
        for (int i = N - 2; i >= 0; --i)
            rightHeight[i] = Math.max(rightHeight[i + 1], height[i]);
        
        int ans = 0;
        for (int i = 0; i < N; ++i) {
            int minHeight = Math.min(leftHeight[i], rightHeight[i]);
            ans += minHeight - height[i];
        }
        return ans;
    }
}

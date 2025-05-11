import java.io.*;
import java.util.StringTokenizer;

public class IQ_Test {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int n = Integer.parseInt(br.readLine());
            int[] arr = new int[51];
            
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) arr[i] = Integer.parseInt(st.nextToken());
            
            if (n <= 2) {
                if (n == 1) {
                    System.out.print("A");
                } else { // n == 2
                    if (arr[0] == arr[1]) System.out.print(arr[0]);
                    else System.out.print("A");
                }
                return;
            }
            
            int tmp  = arr[1] - arr[0], tmp2 = arr[2] - arr[1];
            int a;
            if (tmp != 0) a = tmp2 / tmp;
            else a = 0;
            int b = arr[1] - a * arr[0];
            
            for (int i = 2; i < n; i++) {
                if (arr[i] != arr[i - 1] * a + b) {
                    System.out.print("B");
                    return;
                }
            }
            System.out.print(arr[n - 1] * a + b);
        }
    }
}

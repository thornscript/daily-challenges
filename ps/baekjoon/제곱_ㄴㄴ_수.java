import java.util.*;

public class 제곱_ㄴㄴ_수 {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            long min = sc.nextLong();
            long max = sc.nextLong();

            long ans =  max - min + 1;

            int size = (int)(max - min + 1);
            boolean[] primes = new boolean[size];

            for (long i = 2; i * i <= max; ++i) {
                long t = i * i;

                long start = min / t;
                if (min % t != 0) {
                    start++;
                }

                for (long j = start; j * t <= max; ++j) {
                    int idx = (int)(j * t - min);
                    if (!primes[idx]) {
                        primes[idx] = true;
                        ans--;
                    }
                }
            }

            System.out.println(ans);
        }
    }
}

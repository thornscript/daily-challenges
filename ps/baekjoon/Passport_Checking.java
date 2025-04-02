import java.io.*;
import java.util.*;

public class Passport_Checking {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int N = Integer.parseInt(br.readLine());
            Set<String> stolenPassports = new HashSet<>();
            for (int i = 0; i < N; i++)
                stolenPassports.add(br.readLine().trim());
            
            int M = Integer.parseInt(br.readLine());
            int count = 0;
            for (int i = 0; i < M; i++) {
                String passport = br.readLine().trim();
                if (stolenPassports.contains(passport))
                    count++;
            }
            System.out.println(count);
        }
    }
}

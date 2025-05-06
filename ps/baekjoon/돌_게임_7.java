import java.util.Scanner;

public class 돌_게임_7 {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            long N = sc.nextLong();
            N = N % 5;
            if (N == 0 || N == 2) System.out.println("CY");
            else System.out.println("SK");
        }
    }
}

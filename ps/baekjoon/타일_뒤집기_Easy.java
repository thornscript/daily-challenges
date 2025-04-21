import java.io.*;

public class 타일_뒤집기_Easy {

    static char[][] board, afterBoard;
    static int N;
    static int[] di = {0, 0, 0, 1, -1}, dj = {1, -1, 0, 0, 0};

    static void reverse(char[][] board, int ci, int cj) {
        for (int i = 0; i < 5; ++i) {
            int ni = ci + di[i], nj = cj + dj[i];
            if (!(ni >= 0 && ni < N && nj >= 0 && nj < N)) continue;
            board[ni][nj] = board[ni][nj] == '#' ? '.' : '#';
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
                N = Integer.parseInt(br.readLine());
                board = new char[N][N];
                afterBoard = new char[N][N];

                String firstLine = br.readLine();
                board[0] = firstLine.toCharArray();
                afterBoard[0] = firstLine.toCharArray();

                for (int i = 0; i < N; ++i)
                    if (board[0][i] == '#')
                        reverse(afterBoard, 0, i);
                
                // P행에 마지막으로 영향을 미칠 수 있는 행은 그 다음 행인 P+1행임
                // 즉, 현재 위치에서 #로 표시되어 있는 칸을 반전시켜야 함
                for (int i = 1; i < N; ++i) {
                    for (int j = 0; j < N; ++j) {
                        if (afterBoard[i - 1][j] == '#') {
                            board[i][j] = '#';
                            afterBoard[i][j] = afterBoard[i][j] == '#' ? '.' : '#';
                            reverse(afterBoard, i, j);
                        }
                    }
                }
            
                for (int i = 0; i < N; ++i) {
                    for (int j = 0; j < N; ++j)
                        bw.write(board[i][j] == '#' ? '#' : '.');
                    bw.write('\n');
                }
            }
        }
    }
}

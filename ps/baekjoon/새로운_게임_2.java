import java.util.*;

public class 새로운_게임_2 {
    static final int MAX_SIZE = 12;
    static final int[] dx = {0, 0, -1, 1};
    static final int[] dy = {1, -1, 0, 0};
    
    static class Horse {
        int no;
        int x, y;
        int direction;
        
        Horse(int no, int x, int y, int direction) {
            this.no = no;
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }
    
    static int n, k;
    static int[][] board = new int[MAX_SIZE][MAX_SIZE];
    static ArrayList<Horse>[][] horses = new ArrayList[MAX_SIZE][MAX_SIZE];
    static ArrayList<Horse> orders = new ArrayList<>();
    
    enum BoardType {
        WHITE(0), RED(1), BLUE(2);
        
        private final int value;
        BoardType(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
    
    static boolean isValidPos(int cx, int cy) {
        return cx >= 0 && cx < n && cy >= 0 && cy < n;
    }
    
    static boolean moveHorse(int order) {
        int cx = orders.get(order).x;
        int cy = orders.get(order).y;
        int d = orders.get(order).direction;
        int no = orders.get(order).no;
        
        int c = -1;
        for (int i = 0; i < horses[cx][cy].size(); i++) {
            if (horses[cx][cy].get(i).no == no) {
                c = i;
                break;
            }
        }
        
        int nx = cx + dx[d];
        int ny = cy + dy[d];
        
        if (!isValidPos(nx, ny) || board[nx][ny] == BoardType.BLUE.getValue()) {
            nx -= dx[d] * 2;
            ny -= dy[d] * 2;
            
            if (d == 0) {
                orders.get(order).direction = horses[cx][cy].get(c).direction = 1;
            } else if (d == 1) {
                orders.get(order).direction = horses[cx][cy].get(c).direction = 0;
            } else if (d == 2) {
                orders.get(order).direction = horses[cx][cy].get(c).direction = 3;
            } else if (d == 3) {
                orders.get(order).direction = horses[cx][cy].get(c).direction = 2;
            }
            
            if (!isValidPos(nx, ny) || board[nx][ny] == BoardType.BLUE.getValue()) {
                return true;
            }
        }
        
        if (board[nx][ny] == BoardType.RED.getValue()) {
            Collections.reverse(horses[cx][cy].subList(c, horses[cx][cy].size()));
        }
        
        for (int i = c; i < horses[cx][cy].size(); i++) {
            Horse h = horses[cx][cy].get(i);
            orders.get(h.no).x = h.x = nx;
            orders.get(h.no).y = h.y = ny;
            horses[nx][ny].add(h);
        }
        
        for (int i = horses[cx][cy].size() - 1; i >= c; i--) {
            horses[cx][cy].remove(i);
        }
        
        if (horses[nx][ny].size() >= 4) {
            return false;
        }
        
        return true;
    }
    
    static int simulate() {
        int turn = 1;
        boolean isEnded = false;
        while (!isEnded) {
            for (int i = 0; i < orders.size(); i++) {
                if (!moveHorse(i)) {
                    isEnded = true;
                }
            }
            if (turn > 1000) {
                return -1;
            }
            turn++;
        }
        return turn - 1;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        n = sc.nextInt();
        k = sc.nextInt();
        
        for (int i = 0; i < MAX_SIZE; i++) {
            for (int j = 0; j < MAX_SIZE; j++) {
                horses[i][j] = new ArrayList<>();
            }
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = sc.nextInt();
            }
        }
        
        for (int i = 0; i < k; i++) {
            int x = sc.nextInt() - 1;
            int y = sc.nextInt() - 1;
            int direction = sc.nextInt() - 1;
            Horse h = new Horse(i, x, y, direction);
            horses[x][y].add(h);
            orders.add(h);
        }
        
        System.out.println(simulate());
        
        sc.close();
    }
}

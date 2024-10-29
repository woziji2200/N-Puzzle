package stud.problem.npuzzle;

import core.problem.Action;
import core.problem.State;

import java.util.Collection;
import java.util.Random;

public class Position extends State {
    private int[][] state;  // 当前棋盘矩阵的状态
    private int[] zeroPosition = new int[2]; // 空格的位置
    public int[][] getState() {
        return state;
    }
    public int[] getZeroPosition() {
        return zeroPosition;
    }

    private static long[][] zobristTable; // Random hash values

    static {
        generateZobristTable();
//        System.out.print("zobristTable = {");
    }
    private static void generateZobristTable() {
        Random random = new Random();
        zobristTable = new long[20][20];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                zobristTable[i][j] = random.nextLong();
            }
        }
    }
    public Position(int[][] state) {
        this.state = state;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] == 0) {
                    this.zeroPosition[0] = i;
                    this.zeroPosition[1] = j;
                }
            }
        }
        computeHashCode();
    }


    @Override
    public void draw() {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                System.out.print(state[i][j] + " ");
            }
            System.out.print('\n');
        }
        System.out.print('\n');
    }

    @Override
    public State next(Action action) {
        int[][] nextStateArray = new int[state.length][state[0].length];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                nextStateArray[i][j] = state[i][j];
            }
        }

        if (action instanceof Move) {
            Move move = (Move) action;
            System.out.println("原始状态：");
            this.draw();
            System.out.println("移动方向：" + move.direction);
            int size = state.length;
            int x = zeroPosition[0];
            int y = zeroPosition[1];
            switch (move.direction) {
                case UP:
                    if (x > 0) {
                        nextStateArray[x][y] = nextStateArray[x - 1][y];
                        nextStateArray[x - 1][y] = 0;
//                        nextState.zeroPosition[0] = x - 1;
                    }
                    break;
                case DOWN:
                    if (x < size - 1) {
                        nextStateArray[x][y] = nextStateArray[x + 1][y];
                        nextStateArray[x + 1][y] = 0;
//                        nextState.zeroPosition[0] = x + 1;
                    }
                    break;
                case LEFT:
                    if (y > 0) {
                        nextStateArray[x][y] = nextStateArray[x][y - 1];
                        nextStateArray[x][y - 1] = 0;
//                        nextState.zeroPosition[1] = y - 1;
                    }
                    break;
                case RIGHT:
                    if (y < size - 1) {
                        nextStateArray[x][y] = nextStateArray[x][y + 1];
                        nextStateArray[x][y + 1] = 0;
//                        nextState.zeroPosition[1] = y + 1;
                    }
                    break;
                default:
                    break;
            }
        }
        Position nextState = new Position(nextStateArray);
        System.out.println("移动后状态：");
        nextState.draw();
        return nextState;
    }

    @Override
    public Iterable<? extends Action> actions() {
        Collection<Action> actions = new java.util.ArrayList<>();
        actions.add(new Move(Direction.UP));
        actions.add(new Move(Direction.DOWN));
        actions.add(new Move(Direction.LEFT));
        actions.add(new Move(Direction.RIGHT));
        return actions;
    }

    public int parity() {//Position中的方法
        // 根据Puzzle奇偶阶数分类讨论
        int size = this.state.length;
        int inv=(size%2==0)?zeroPosition[0]:0;
        for(int i=0;i<size;++i) {
            for(int j=0;j<size;++j) {
                //遍历state[i][j]之后的数，找到比state[i][j]小的数
                for(int ti=i,tj=j+1;ti<size;++ti) {
                    for(;tj<size;++tj) {
                        if(this.state[ti][tj]<this.state[i][j]
                                &&this.state[ti][tj]!=0&&this.state[i][j]!=0) {
                            ++inv;
                        }
                    }
                    tj=0;
                }
            }
        }
        return inv%2;
    }

    private long hashCode2;
    public long getHashCode() {
        return hashCode2;
    }
    private void computeHashCode(){
        long hash = 0;
        for (int row = 0; row < state.length; row++) {
            for (int col = 0; col < state[row].length; col++) {
                int piece = state[row][col];
                if (piece != 0) { // Assuming 0 means empty
                    hash ^= zobristTable[piece][row * state[row].length + col];
                }
            }
        }
        hashCode2 = hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position other = (Position) obj;
//            other.draw();
//            this.draw();
//            System.out.println("other.getHashCode() = " + other.getHashCode());
//            System.out.println("this.getHashCode() = " + this.getHashCode());
//            System.out.println("other.getHashCode() == this.getHashCode() = " + (other.getHashCode() == this.getHashCode()));
//            return getHashCode() == other.getHashCode();
            for (int i = 0; i < state.length; i++) {
                for (int j = 0; j < state[i].length; j++) {
                    if (state[i][j] != other.state[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}

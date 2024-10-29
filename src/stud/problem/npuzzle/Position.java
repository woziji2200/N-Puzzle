package stud.problem.npuzzle;

import core.problem.Action;
import core.problem.State;

import java.util.Collection;
import java.util.Random;


/**
 * 我们认为Position创建以后，棋盘的state就不会再改变了。所以hashCode和zeroPosition只需要计算一次
 */
public class Position extends State {
    private int[][] state;  // 当前棋盘矩阵的状态
    private int[] zeroPosition = {-1, -1}; // 空格的位置
    private int size;
    public int[][] getState() {
        return state;
    }
    public int[] getZeroPosition() {
        if(zeroPosition[0] != -1 && zeroPosition[1] != -1) {
            return zeroPosition;
        }
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                if (state[i][j] == 0) {
                    this.zeroPosition[0] = i;
                    this.zeroPosition[1] = j;
                }
            }
        }
        return zeroPosition;
    }
    public int getSize() {
        return size;
    }

    private static int[][] zobristTable; // Random hash values
    private static boolean hasZobristTable = false;
    private static void generateZobristTable() {
        Random random = new Random();
        zobristTable = new int[20][20];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                zobristTable[i][j] = random.nextInt();
            }
        }
    }
    public Position(int[][] state) {
        if (!hasZobristTable) {
            generateZobristTable();
            System.out.println("生成ZobristTable");
            hasZobristTable = true;
        }
        this.state = state;
        this.size = state.length;
    }

    public Position(int[][] state, int[] zeroPosition2) {
        if (!hasZobristTable) {
            generateZobristTable();
            System.out.println("生成ZobristTable");
            hasZobristTable = true;
        }
        this.state = state;
        this.size = state.length;
        this.zeroPosition = zeroPosition2;
    }


    @Override
    public void draw() {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                System.out.print(state[i][j] + " ");
            }
            System.out.print('\n');
        }
        System.out.print('\n');
    }

    @Override
    public State next(Action action) {
        int[][] nextStateArray = new int[getSize()][getSize()];
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                nextStateArray[i][j] = state[i][j];
            }
        }

        if (action instanceof Move) {
            Move move = (Move) action;
//            System.out.println("原始状态：");
//            this.draw();
//            System.out.println("移动方向：" + move.direction);
            int size = getSize();
            int x = getZeroPosition()[0];
            int y = getZeroPosition()[1];
            int[] zeroPosition2 = {x, y};
            switch (move.direction) {
                case UP:
                    if (x > 0) {
                        nextStateArray[x][y] = nextStateArray[x - 1][y];
                        nextStateArray[x - 1][y] = 0;
                        zeroPosition2[0] = x - 1;
                    }
                    break;
                case DOWN:
                    if (x < size - 1) {
                        nextStateArray[x][y] = nextStateArray[x + 1][y];
                        nextStateArray[x + 1][y] = 0;
                        zeroPosition2[0] = x + 1;
                    }
                    break;
                case LEFT:
                    if (y > 0) {
                        nextStateArray[x][y] = nextStateArray[x][y - 1];
                        nextStateArray[x][y - 1] = 0;
                        zeroPosition2[1] = y - 1;
                    }
                    break;
                case RIGHT:
                    if (y < size - 1) {
                        nextStateArray[x][y] = nextStateArray[x][y + 1];
                        nextStateArray[x][y + 1] = 0;
                        zeroPosition2[1] = y + 1;
                    }
                    break;
                default:
                    break;
            }
            Position nextState = new Position(nextStateArray, zeroPosition2);
            return nextState;
        }
        return null;

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

    @Override
    public int hashCode() {
//        int result = 1;
//        for (int[] row : state) {
//            for (int pos : row) {
//                result = 31 * result + pos;
//            }
//        }
//        return result;
        return getZHashCode();
    }

    private int zHashCode = 0;
    public int getZHashCode() {
        if(zHashCode == 0) {
            int hash = 0;
            for (int i = 0; i < getSize(); i++) {
                for (int j = 0; j < getSize(); j++) {
                    int piece = state[i][j];
                    if (piece != 0) { // 0表示空格
                        hash ^= zobristTable[piece][i * getSize() + j];
                    }
                }
            }
            zHashCode = hash;
        }
        return zHashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position other = (Position) obj;
            boolean flag = true;
            if(getSize() != other.getSize() || getSize() != other.getSize()) {
                return false;
            }
//            System.out.println("zHashCode = " + getZHashCode());
//            System.out.println("other.zHashCode = " + other.getZHashCode());
//            if(getZHashCode() == 0){
//                draw();
//
//            }
            return hashCode() == other.hashCode();
//            return getZHashCode() == other.getZHashCode();
//            for (int i = 0; i < state.length; i++) {
//                for (int j = 0; j < state[i].length; j++) {
//                    if (state[i][j] != other.state[i][j]) {
////                        return false;
//                        flag = false;
//                    }
//                }
//            }
//            return true;
//            draw();
//            other.draw();
//            System.out.println("flag = " + flag);
//            return flag;
        }

        return false;
    }
}

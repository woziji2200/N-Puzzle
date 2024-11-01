package stud.problem.npuzzle;

import core.problem.Action;
import core.problem.Problem;
import core.problem.State;
import core.solver.queue.Node;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Deque;

public class PuzzleFinding extends Problem {
    public PuzzleFinding(State initialState, State goal) {
        super(initialState, goal);
    }

    public State getGoal() {
        return goal;
    }

    @Override
    public boolean solvable() {
//        return ((Position)initialState).parity() == ((Position)goal).parity();
        // 将初始状态转换为 Position 类型，这个类包含了拼图的当前布局
        Position puzzleState = (Position) initialState;
        // 获取拼图的大小，即 N×N 网格中的 N
        int boardSize = puzzleState.getSize();
        // 获取当前拼图的布局
        int[][] boardLayout = puzzleState.getState();
        // 初始化逆序数计数器
        int inversionCount = 0;
        // 创建一个一维数组 flatLayout 来存储拼图的布局，以便进行逆序数的计算
        int[] flatLayout = new int[boardSize * boardSize];
        // 将拼图布局复制到 flatLayout 数组中
        int index = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                flatLayout[index++] = boardLayout[i][j];
            }
        }
        // 重置 index 为 0，用于计算逆序数
        index = 0;
        // 遍历 flatLayout 数组，计算逆序数
        for (int i = 0; i < boardSize * boardSize; i++) {
            for (int j = i + 1; j < boardSize * boardSize; j++) {
                // 如果 flatLayout[j] 小于 flatLayout[i] 且 flatLayout[j] 不为 0，则增加逆序数计数器
                if (flatLayout[j] < flatLayout[i] && flatLayout[j] != 0) {
                    inversionCount++;
                }
            }
        }
        // 获取空格的位置
        int[] blankPosition = puzzleState.getZeroPosition();
        // 获取空格的行号
        int blankRow = blankPosition[0];
        // 如果拼图的大小是奇数
        if (boardSize % 2 == 1) {
            // 如果逆序数是偶数，则拼图有解
            return inversionCount % 2 == 0;
        }
        // 如果拼图的大小是偶数
        else {
            // 如果逆序数的奇偶性和空格的行号的奇偶性不同，则拼图有解
            return (inversionCount % 2 == 0 && blankRow % 2 == 1) ||
                    (inversionCount % 2 == 1 && blankRow % 2 == 0);
        }
    }

    @Override
    public int stepCost(State state, Action action) {
        return 1;
    }

    @Override
    public boolean applicable(State state, Action action) {
        // 将当前状态转换为 Position 类型，这个类包含了拼图的当前布局
        Position puzzleState = (Position) state;
        // 获取拼图的大小，即 N×N 网格中的 N
        int boardSize = puzzleState.getSize();
        // 获取当前拼图的布局
        int[][] boardLayout = puzzleState.getState();
        // 获取空格的位置
        int[] blankPosition = puzzleState.getZeroPosition();
        // 获取空格的行号
        int blankRow = blankPosition[0];
        // 获取空格的列号
        int blankColumn = blankPosition[1];
        // 获取移动方向
        Move move = (Move) action;
        // 如果移动方向是向上
        if (move.direction == Direction.UP) {
            // 如果空格在第一行，则无法向上移动
            return blankRow != 0;
        }
        // 如果移动方向是向下
        else if (move.direction == Direction.DOWN) {
            // 如果空格在最后一行，则无法向下移动
            return blankRow != boardSize - 1;
        }
        // 如果移动方向是向左
        else if (move.direction == Direction.LEFT) {
            // 如果空格在第一列，则无法向左移动
            return blankColumn != 0;
        }
        // 如果移动方向是向右
        else {
            // 如果空格在最后一列，则无法向右移动
            return blankColumn != boardSize - 1;
        }
    }

    @Override
    public void showSolution(Deque<Node> path) {
        if (path == null){
            System.out.println("No Solution.");
            return;
        }
        System.out.println("=======");
        initialState.draw();
        for (Node node : path) {
            Move move = (Move) (node.getAction());
            move.draw();
            Position position = (Position) (node.getState());
            position.draw();
        }
        System.out.println("=======");
    }
}

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
        // ����ʼ״̬ת��Ϊ Position ���ͣ�����������ƴͼ�ĵ�ǰ����
        Position puzzleState = (Position) initialState;
        // ��ȡƴͼ�Ĵ�С���� N��N �����е� N
        int boardSize = puzzleState.getSize();
        // ��ȡ��ǰƴͼ�Ĳ���
        int[][] boardLayout = puzzleState.getState();
        // ��ʼ��������������
        int inversionCount = 0;
        // ����һ��һά���� flatLayout ���洢ƴͼ�Ĳ��֣��Ա�����������ļ���
        int[] flatLayout = new int[boardSize * boardSize];
        // ��ƴͼ���ָ��Ƶ� flatLayout ������
        int index = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                flatLayout[index++] = boardLayout[i][j];
            }
        }
        // ���� index Ϊ 0�����ڼ���������
        index = 0;
        // ���� flatLayout ���飬����������
        for (int i = 0; i < boardSize * boardSize; i++) {
            for (int j = i + 1; j < boardSize * boardSize; j++) {
                // ��� flatLayout[j] С�� flatLayout[i] �� flatLayout[j] ��Ϊ 0��������������������
                if (flatLayout[j] < flatLayout[i] && flatLayout[j] != 0) {
                    inversionCount++;
                }
            }
        }
        // ��ȡ�ո��λ��
        int[] blankPosition = puzzleState.getZeroPosition();
        // ��ȡ�ո���к�
        int blankRow = blankPosition[0];
        // ���ƴͼ�Ĵ�С������
        if (boardSize % 2 == 1) {
            // �����������ż������ƴͼ�н�
            return inversionCount % 2 == 0;
        }
        // ���ƴͼ�Ĵ�С��ż��
        else {
            // �������������ż�ԺͿո���кŵ���ż�Բ�ͬ����ƴͼ�н�
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
        // ����ǰ״̬ת��Ϊ Position ���ͣ�����������ƴͼ�ĵ�ǰ����
        Position puzzleState = (Position) state;
        // ��ȡƴͼ�Ĵ�С���� N��N �����е� N
        int boardSize = puzzleState.getSize();
        // ��ȡ��ǰƴͼ�Ĳ���
        int[][] boardLayout = puzzleState.getState();
        // ��ȡ�ո��λ��
        int[] blankPosition = puzzleState.getZeroPosition();
        // ��ȡ�ո���к�
        int blankRow = blankPosition[0];
        // ��ȡ�ո���к�
        int blankColumn = blankPosition[1];
        // ��ȡ�ƶ�����
        Move move = (Move) action;
        // ����ƶ�����������
        if (move.direction == Direction.UP) {
            // ����ո��ڵ�һ�У����޷������ƶ�
            return blankRow != 0;
        }
        // ����ƶ�����������
        else if (move.direction == Direction.DOWN) {
            // ����ո������һ�У����޷������ƶ�
            return blankRow != boardSize - 1;
        }
        // ����ƶ�����������
        else if (move.direction == Direction.LEFT) {
            // ����ո��ڵ�һ�У����޷������ƶ�
            return blankColumn != 0;
        }
        // ����ƶ�����������
        else {
            // ����ո������һ�У����޷������ƶ�
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

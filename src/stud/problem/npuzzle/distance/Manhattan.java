package stud.problem.npuzzle.distance;

import core.problem.State;
import core.solver.algorithm.heuristic.Predictor;
import stud.problem.npuzzle.Position;

public class Manhattan implements Predictor {
    @Override
    public int heuristics(State state, State goal) {
        int [][]stateArray = ((Position) state).state;
        int [][]goalArray = ((Position) goal).state;
        int size = stateArray.length;
        int manhattanDistance = 0;

        // ���� stateArray������ÿ�����ֵ����� goalArray �е�λ�õ������پ���
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                int value = stateArray[x][y];
                if (value != 0) { // ���Կհ׿�
                    // �ҵ� value �� goalArray �е�λ��
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            if (goalArray[i][j] == value) {
                                // ���㵱ǰλ�� (x, y) ��Ŀ��λ�� (i, j) �������پ���
                                manhattanDistance += Math.abs(x - i) + Math.abs(y - j);
                            }
                        }
                    }
                }
            }
        }

        return manhattanDistance;
    }
}

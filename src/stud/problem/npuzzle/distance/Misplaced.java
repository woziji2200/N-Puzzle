package stud.problem.npuzzle.distance;

import core.problem.State;
import core.solver.algorithm.heuristic.Predictor;
import stud.problem.npuzzle.Position;

public class Misplaced implements Predictor {
    @Override
    public int heuristics(State state, State goal) {
        int [][]stateArray = ((Position) state).state;
        int [][]goalArray = ((Position) goal).state;

        int size = stateArray.length;
        int misplacedTiles = 0;

        // ���� stateArray �� goalArray ���㲻��λ�Ŀ���
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                // �����鲻��Ŀ��λ�ã��Ҳ��ǿհ׿飨0��ʱ�������λ����
                if (stateArray[x][y] != 0 && stateArray[x][y] != goalArray[x][y]) {
                    misplacedTiles++;
                }
            }
        }

        return misplacedTiles;
    }
}

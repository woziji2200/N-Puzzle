package stud.problem.npuzzle.distance;

import core.problem.State;
import core.solver.algorithm.heuristic.Predictor;
import stud.problem.npuzzle.Position;

public class Misplaced implements Predictor {
    @Override
    public int heuristics(State state, State goal) {
        int [][]stateArray = ((Position) state).getState();
        int [][]goalArray = ((Position) goal).getState();

        int size = stateArray.length;
        int misplacedTiles = 0;

        // 遍历 stateArray 和 goalArray 计算不在位的块数
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                // 仅当块不在目标位置，且不是空白块（0）时，计入错位计数
                if (stateArray[x][y] != 0 && stateArray[x][y] != goalArray[x][y]) {
                    misplacedTiles++;
                }
            }
        }
        System.out.println("misplacedTiles = " + misplacedTiles);
        return misplacedTiles;
    }
}

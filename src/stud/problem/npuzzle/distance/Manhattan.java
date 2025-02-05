package stud.problem.npuzzle.distance;

import core.problem.State;
import core.solver.algorithm.heuristic.Predictor;
import stud.problem.npuzzle.Position;

import java.util.HashMap;
import java.util.Map;

public class Manhattan implements Predictor {
    @Override
    public int heuristics(State state, State goal) {
        int [][]stateArray = ((Position) state).getState();
        int [][]goalArray = ((Position) goal).getState();
        int size = stateArray.length;
        int manhattanDistance = 0;

//        // 遍历 stateArray，计算每个数字到其在 goalArray 中的位置的曼哈顿距离
//        for (int x = 0; x < size; x++) {
//            for (int y = 0; y < size; y++) {
//                int value = stateArray[x][y];
//                if (value != 0) { // 忽略空白块
//                    // 找到 value 在 goalArray 中的位置
//                    for (int i = 0; i < size; i++) {
//                        for (int j = 0; j < size; j++) {
//                            if (goalArray[i][j] == value) {
//                                // 计算当前位置 (x, y) 到目标位置 (i, j) 的曼哈顿距离
//                                manhattanDistance += Math.abs(x - i) + Math.abs(y - j);
//                            }
//                        }
//                    }
//                }
//            }
//        }

        Map<Integer, int[]> goalPositionMap = new HashMap<>();
        // 构建每个值在 goalArray 中的位置映射
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                goalPositionMap.put(goalArray[i][j], new int[]{i, j});
            }
        }

        // 计算曼哈顿距离
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                int value = stateArray[x][y];
                if (value != 0) { // 忽略空白块
                    int[] goalPos = goalPositionMap.get(value);
                    manhattanDistance += Math.abs(x - goalPos[0]) + Math.abs(y - goalPos[1]);
                }
            }
        }
        return manhattanDistance;

    }
}

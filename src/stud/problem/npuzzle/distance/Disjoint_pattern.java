package stud.problem.npuzzle.distance;

import core.problem.State;
import core.solver.algorithm.heuristic.Predictor;
import stud.problem.npuzzle.Database;
import stud.problem.npuzzle.Position;

import java.util.HashMap;

public class Disjoint_pattern implements Predictor {
    static HashMap<Integer, Integer> pattern1;
    static HashMap<Integer, Integer> pattern2;
    static HashMap<Integer, Integer> pattern3;
    static {
//        pattern1 =  Database.getDatabase(new Position(new int[][]{{1, 2, 3, 4}, {5, 16, 16, 16}, {16, 16, 16, 16}, {16, 16, 16, 0}}),"1.db");
//        pattern2 =  Database.getDatabase(new Position(new int[][]{{16, 16, 16, 16}, {16, 6, 7, 8}, {9, 10, 16, 16}, {16, 16, 16, 0}}),"2.db");
//        pattern3 =  Database.getDatabase(new Position(new int[][]{{16, 16, 16, 16}, {16, 16, 16, 16}, {16, 16, 11, 12}, {13, 14, 15, 0}}),"3.db");
//             2 3 4
            pattern1 =  Database.getDatabase(new Position(new int[][]{{16, 2, 3, 4}, {16, 16, 16, 16}, {16, 16, 16, 16}, {16, 16, 16, 0}}),"1.db");
            System.out.println("pattern1 success");
//             1 5 6 9 10 13
            pattern2 =  Database.getDatabase(new Position(new int[][]{{1, 16, 16, 16}, {5, 6, 16, 16}, {9, 10,16, 16}, {13, 16, 16, 0}}),"2.db");
            System.out.println("pattern2 success");
//             7 8 11 12 14 15
            pattern3 =  Database.getDatabase(new Position(new int[][]{{16, 16, 16, 16}, {16, 16, 7, 8}, {16, 16, 11, 12}, {16, 14, 15, 0}}),"3.db");
            System.out.println("pattern3 success");

        System.out.println("Loading database success");
    }

    @Override
    public int heuristics(State state, State goal) {
        int[][] stateArray = ((Position) state).getState();
        int[][][] states = new int[3][4][4];
        int disjHash1 = 0;
        int disjHash2 = 0;
        int disjHash3 = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int num = stateArray[i][j];
//                if (num >= 1 && num <= 5) {
//                    states[0][i][j] = num;
//                    states[1][i][j] = 16;
//                    states[2][i][j] = 16;
//                } else if (num >= 6 && num <= 10) {
//                    states[1][i][j] = num;
//                    states[0][i][j] = 16;
//                    states[2][i][j] = 16;
//                } else if (num >= 11 && num <= 15) {
//                    states[2][i][j] = num;
//                    states[0][i][j] = 16;
//                    states[1][i][j] = 16;
//                }


//                if (num >= 1 && num <= 5) {
//                    states[0][i][j] = num;
//                    states[1][i][j] = 0;
//                    states[2][i][j] = 0;
//                } else if (num >= 6 && num <= 10) {
//                    states[1][i][j] = num;
//                    states[0][i][j] = 0;
//                    states[2][i][j] = 0;
//                } else if (num >= 11 && num <= 15) {
//                    states[2][i][j] = num;
//                    states[0][i][j] = 0;
//                    states[1][i][j] = 0;
//                }
//                // 2 3 4
                if (num == 2 || num == 3 || num == 4 ) {
                    states[0][i][j] = num;
                    states[1][i][j] = 16;
                    states[2][i][j] = 16;
                    // 直接生成hash值，不用再调用Position的hashCode方法了
                    disjHash1 ^= Position.zobristTable[num][(i) * 4 + j];
                }
//                // 1 5 6 9 10 13
                else if (num == 1 || num == 5 || num == 6 || num == 9 || num == 10 || num == 13) {
                    states[1][i][j] = num;
                    states[0][i][j] = 16;
                    states[2][i][j] = 16;
                    disjHash2 ^= Position.zobristTable[num][(i) * 4 + j];
                }
//                // 7 8 11 12 14 15
                else if (num == 7 || num == 8 || num == 11 || num == 12 || num == 14 || num == 15) {
                    states[2][i][j] = num;
                    states[0][i][j] = 16;
                    states[1][i][j] = 16;
                    disjHash3 ^= Position.zobristTable[num][(i) * 4 + j];
                }
            }
        }
        Position state1Position = new Position(states[0], true, disjHash1);
        Position state2Position = new Position(states[1], true, disjHash2);
        Position state3Position = new Position(states[2], true, disjHash3);
        int distance1 , distance2 , distance3;
        try {
             distance1 = pattern1.get(state1Position.getDisjointPatternHashCode());
             distance2 = pattern2.get(state2Position.getDisjointPatternHashCode());
             distance3 = pattern3.get(state3Position.getDisjointPatternHashCode());
        } catch (Exception e) {
            state3Position.draw();
            throw new RuntimeException(e);
        }
        return (int) ((distance1 + distance2 + distance3));
    }
}

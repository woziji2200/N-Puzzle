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
        pattern1 =  Database.getDatabase(new Position(new int[][]{{1, 2, 3, 4}, {5, 16, 16, 16}, {16, 16, 16, 16}, {16, 16, 16, 0}}));
        pattern2 =  Database.getDatabase(new Position(new int[][]{{16, 16, 16, 16}, {16, 6, 7, 8}, {9, 10, 16, 16}, {16, 16, 16, 0}}));
        pattern3 =  Database.getDatabase(new Position(new int[][]{{16, 16, 16, 16}, {16, 16, 16, 16}, {16, 16, 11, 12}, {13, 14, 15, 0}}));
//        pattern1 =  Database.getDatabase(new Position(new int[][]{{1, 2, 3, 4}, {5, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}}));
//        pattern2 =  Database.getDatabase(new Position(new int[][]{{0, 0, 0, 0}, {0, 6, 7, 8}, {9, 10, 0, 0}, {0, 0, 0, 0}}));
//        pattern3 =  Database.getDatabase(new Position(new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 11, 12}, {13, 14, 15, 0}}));

//        pattern1 =  Database.getDatabase(new Position(new int[][]{{16, 2, 3, 4}, {16, 16, 16, 16}, {16, 16, 16, 16}, {13, 14, 16, 0}}));
//        pattern2 =  Database.getDatabase(new Position(new int[][]{{1, 16, 16, 16}, {5, 6, 16, 16}, {9, 10, 16, 16}, {16, 16, 16, 0}}));
//        pattern3 =  Database.getDatabase(new Position(new int[][]{{16, 16, 16, 16}, {16, 16, 7, 8}, {16, 16, 11, 12}, {16, 16, 15, 0}}));
        System.out.println("Loading database success");
    }

    @Override
    public int heuristics(State state, State goal) {
        int[][] stateArray = ((Position) state).getState();
        int[][][] states = new int[3][4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int num = stateArray[i][j];
                if (num >= 1 && num <= 5) {
                    states[0][i][j] = num;
                    states[1][i][j] = 16;
                    states[2][i][j] = 16;
                } else if (num >= 6 && num <= 10) {
                    states[1][i][j] = num;
                    states[0][i][j] = 16;
                    states[2][i][j] = 16;
                } else if (num >= 11 && num <= 15) {
                    states[2][i][j] = num;
                    states[0][i][j] = 16;
                    states[1][i][j] = 16;
                }
//                // 2 3 4 13 14
//                if (num == 2 || num == 3 || num == 4 || num == 13 || num == 14) {
//                    states[0][i][j] = num;
//                    states[1][i][j] = 16;
//                    states[2][i][j] = 16;
//                }
//                // 1 5 6 9 10
//                else if (num == 1 || num == 5 || num == 6 || num == 9 || num == 10) {
//                    states[1][i][j] = num;
//                    states[0][i][j] = 16;
//                    states[2][i][j] = 16;
//                }
//                // 7 8 11 12 15
//                else if (num == 7 || num == 8 || num == 11 || num == 12 || num == 15) {
//                    states[2][i][j] = num;
//                    states[0][i][j] = 16;
//                    states[1][i][j] = 16;
//                }
            }
        }
        Position state1Position = new Position(states[0], true);
        Position state2Position = new Position(states[1], true);
        Position state3Position = new Position(states[2], true);
        int distance1 = pattern1.get(state1Position.hashCode());
        int distance2 = pattern2.get(state2Position.hashCode());
        int distance3 = pattern3.get(state3Position.hashCode());
//        System.out.println("distance1 = " + distance1);
//        System.out.println("distance2 = " + distance2);
//        System.out.println("distance3 = " + distance3);
//        System.out.println("distance1 + distance2 + distance3 = " + (distance1 + distance2 + distance3));
//        try {
//            Thread.sleep(20);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("distance1 + distance2 + distance3 = " + (distance1 + distance2 + distance3));
        return distance1 + distance2 + distance3;
    }
}

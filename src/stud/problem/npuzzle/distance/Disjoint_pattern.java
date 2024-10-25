package stud.problem.npuzzle.distance;

import core.problem.State;
import core.solver.algorithm.heuristic.Predictor;
import stud.problem.npuzzle.Position;

public class Disjoint_pattern implements Predictor {

    @Override
    public int heuristics(State state, State goal) {
        int [][]stateArray = ((Position) state).state;
        int [][]goalArray = ((Position) goal).state;

        return 0;
    }
}

package stud.runner;

import core.problem.Problem;
import core.runner.EngineFeeder;
import core.solver.algorithm.heuristic.HeuristicType;
import core.solver.algorithm.heuristic.Predictor;
import core.solver.queue.EvaluationType;
import core.solver.queue.Frontier;

import java.util.ArrayList;

public class PuzzleFeeder extends EngineFeeder {
    @Override
    public ArrayList<Problem> getProblems(ArrayList<String> problemLines) {
        System.out.println("PuzzleFeeder.getProblems"+ problemLines);
        return null;
    }

    @Override
    public Frontier getFrontier(EvaluationType type) {
        return null;
    }

    @Override
    public Predictor getPredictor(HeuristicType type) {
        return null;
    }
}

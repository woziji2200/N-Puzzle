package stud.runner;

import core.problem.Problem;
import core.runner.EngineFeeder;
import core.solver.algorithm.heuristic.HeuristicType;
import core.solver.algorithm.heuristic.Predictor;
import core.solver.queue.EvaluationType;
import core.solver.queue.Frontier;
import core.problem.*;
import stud.problem.npuzzle.Position;
import stud.problem.npuzzle.PuzzleFinding;

import java.util.ArrayList;

public class PuzzleFeeder extends EngineFeeder {
    @Override
    public ArrayList<Problem> getProblems(ArrayList<String> problemLines) {
        ArrayList<Problem> problems = new ArrayList<>();
        for (String line : problemLines) {

            // ���ɳ�ʼ״̬
            Position initState = new Position();
            // line�� 3 5 0 8 4 2 1 7 3 6 1 2 3 4 5 6 7 8 0
            // ��һ������3��ʾ3*3�ľ���5 0 8 4 2 1 7 3 6 ��ʾ�����ʼ״̬��1 2 3 4 5 6 7 8 0��ʾ�����Ŀ��״̬
            String[] parts = line.split(" ");
            int size = Integer.parseInt(parts[0]);  // ��ȡ����Ĵ�С
            int[][] state = new int[size][size];    // ��ʼ������
            int index = 1;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    state[i][j] = Integer.parseInt(parts[index++]);
                }
            }
            initState.setState(state);
            initState.setSize(size);

            // ����Ŀ��״̬
            Position goal = new Position();
            int[][] goalState = new int[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    goalState[i][j] = Integer.parseInt(parts[index++]);
                }
            }
            goal.setState(goalState);
            goal.setSize(size);
            PuzzleFinding problem = new PuzzleFinding(initState, goal);
            problems.add(problem);
        }
        return problems;
    }

    @Override
    public Frontier getFrontier(EvaluationType type) {
        System.out.println("PuzzleFeeder.getFrontier"+ type);
        return null;
    }

    @Override
    public Predictor getPredictor(HeuristicType type) {
//        System.out.println("PuzzleFeeder.getPredictor"+ type);
        return null;
    }
}

package stud.solver;

import core.problem.Problem;
import core.solver.algorithm.searcher.AbstractSearcher;
import core.solver.algorithm.heuristic.Predictor;
import core.solver.queue.Frontier;
import core.solver.queue.Node;

import java.util.Deque;

/**
 * ���������A*�㷨����Ҫͬѧ���Լ���д���
 */
public class IdAStar extends AbstractSearcher {
    Predictor predictor;
    public IdAStar(Frontier frontier, Predictor predictor) {
        super(frontier); // Frontier�������������һ����ʹ�ö�ջ��ΪFrontier
        //FixMe
        this.predictor = predictor;
    }

    @Override
    public Deque<Node> search(Problem problem) {
        //FixMe
        return null;
    }

}

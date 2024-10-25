package stud.solver;

import core.problem.Problem;
import core.solver.algorithm.searcher.AbstractSearcher;
import core.solver.algorithm.heuristic.Predictor;
import core.solver.queue.Frontier;
import core.solver.queue.Node;

import java.util.Deque;

/**
 * 迭代加深的A*算法，需要同学们自己编写完成
 */
public class IdAStar extends AbstractSearcher {
    Predictor predictor;
    public IdAStar(Frontier frontier, Predictor predictor) {
        super(frontier); // Frontier跟深度优先搜索一样，使用堆栈作为Frontier
        //FixMe
        this.predictor = predictor;
    }

    @Override
    public Deque<Node> search(Problem problem) {
        //FixMe
        return null;
    }

}

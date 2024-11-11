package stud.solver;

import core.problem.Problem;
import core.solver.algorithm.heuristic.Predictor;
import core.solver.algorithm.searcher.AbstractSearcher;
import core.solver.queue.Frontier;
import core.solver.queue.Node;
import java.util.*;

/**
 * 迭代加深的A*算法，需要同学们自己编写完成
 */
public class IdAStar extends AbstractSearcher {
    Predictor predictor;
    private int explored = 0;
    private int expanded = 0;
    // 返回的路径
    Deque<Node> result = new ArrayDeque<>();
    Problem local_problem;

    public IdAStar(Frontier frontier,Predictor predictor) {
        super(frontier);
        this.predictor = predictor;
    }
    /**
     *
     * @param node  当前状态
     * @param parentNode 上一状态
     * @param depth  最大探索深度
     * @return
     */
    boolean dfs(Node node, Node parentNode, int depth){
        if (node.getPathCost() >= depth)
            return false;
        if (local_problem.goal(node.getState())) {
            //如果抵达目标状态，回溯得到路径
            result = generatePath(node);
            return true;
        }
        //添加扩展结点
        expanded++;
        //生成子结点
        List<Node> children = local_problem.childNodes(node, predictor);
        //对每个子结点进行迭代
        for (var child : children){
            explored++;
            //确保不会回到上一步
            if (parentNode != null)
                if(child.equals(parentNode))
                    continue;
            //小于最大深度则继续迭代
            if (child.evaluation() < depth && dfs(child, node, depth)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public Deque<Node> search(Problem problem) {
        local_problem = problem;
        //是否可解
        if (!local_problem.solvable()) {
            System.out.println("No Solution!");
            return null;
        }
        // 起始节点root
        Node root = local_problem.root(predictor);
        //最大探索深度
        int depth = root.getHeuristic();
        while (!dfs(root, null, depth)) {
            depth++;
        }
        return result;
    }
    @Override
    public int nodesExpanded() {
        return expanded;
    }
    @Override
    public int nodesGenerated() {
        return explored;
    }
}

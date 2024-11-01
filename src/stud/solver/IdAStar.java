package stud.solver;

import core.problem.Problem;
import core.problem.State;
import core.solver.algorithm.heuristic.Predictor;
import core.solver.algorithm.searcher.AbstractSearcher;
import core.solver.queue.Frontier;
import core.solver.queue.Node;
import stud.queue.ListFrontier;

import java.util.*;

public class IdAStar extends AbstractSearcher {

    private final Predictor predictor;

    //裁剪阈值
    private int cutoff;
    //下一轮迭代的裁剪阈值
    private int newCutoff;
    //最大迭代深度
    private int maxIteratorDepth = 256;
    //统计扩展结点数
    private int expanded = 0;
    private int generated = 0;

    private final Stack<Node> openStack;
    //private final HashMap<Integer, Integer> closeStack;

    public IdAStar(Frontier frontier,Predictor predictor) {
        super(frontier);
        this.predictor = predictor;
        openStack = new Stack<Node>();
        //closeStack = new HashMap<Integer, Integer>();
    }

    @Override
    public Deque<Node> search(Problem problem) {
        if (!problem.solvable()){
            return null;
        }
        //获取根节点
        openStack.clear();
        //closeStack.clear();
        Node root = problem.root(predictor);
        cutoff = root.evaluation();

        while (cutoff < maxIteratorDepth) {
            openStack.push(root);
            newCutoff = cutoff;
//            System.out.println(newCutoff);
            //当栈未空时继续，执行带裁剪值的深度优先搜索
            expanded = 0;
            while (!openStack.empty()) {
                expanded++;
                Node node = openStack.pop();
                //更新裁剪值为未被探索节点中最小的评估值
                if (problem.goal(node.getState())) {
//                    System.out.println("cutoff="+cutoff+" expanded="+expanded);
                    return generatePath(node);
                }
                //当小于等于裁剪值时，继续向深处搜索
                for (Node child : problem.childNodes(node, predictor)) {
                    //剪枝，防止节点探索回到父节点
                    if (child.evaluation() <= cutoff) {
                        if (node.getParent() == null || !node.getParent().equals(child)) {
                            openStack.push(child);
                        }
                    } else {
                        //记录大于当前cutoff的最小值
                        newCutoff = (newCutoff > cutoff) ? (Math.min(child.evaluation(), newCutoff)) : child.evaluation();
                        //System.out.println("cutoff="+cutoff+" newcutoff="+newCutoff+" child.f="+child.evaluation());
                    }
                }
            }
            //更新裁剪值
            cutoff = newCutoff;
        }
        return null;
    }

    @Override
    public int nodesExpanded() {
        return expanded;
    }

    @Override
    public int nodesGenerated() {
        return generated;
    }
}


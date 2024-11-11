package stud.solver;

import core.problem.Problem;
import core.solver.algorithm.heuristic.Predictor;
import core.solver.algorithm.searcher.AbstractSearcher;
import core.solver.queue.Frontier;
import core.solver.queue.Node;
import java.util.*;

/**
 * ���������A*�㷨����Ҫͬѧ���Լ���д���
 */
public class IdAStar extends AbstractSearcher {
    Predictor predictor;
    private int explored = 0;
    private int expanded = 0;
    // ���ص�·��
    Deque<Node> result = new ArrayDeque<>();
    Problem local_problem;

    public IdAStar(Frontier frontier,Predictor predictor) {
        super(frontier);
        this.predictor = predictor;
    }
    /**
     *
     * @param node  ��ǰ״̬
     * @param parentNode ��һ״̬
     * @param depth  ���̽�����
     * @return
     */
    boolean dfs(Node node, Node parentNode, int depth){
        if (node.getPathCost() >= depth)
            return false;
        if (local_problem.goal(node.getState())) {
            //����ִ�Ŀ��״̬�����ݵõ�·��
            result = generatePath(node);
            return true;
        }
        //�����չ���
        expanded++;
        //�����ӽ��
        List<Node> children = local_problem.childNodes(node, predictor);
        //��ÿ���ӽ����е���
        for (var child : children){
            explored++;
            //ȷ������ص���һ��
            if (parentNode != null)
                if(child.equals(parentNode))
                    continue;
            //С�����������������
            if (child.evaluation() < depth && dfs(child, node, depth)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public Deque<Node> search(Problem problem) {
        local_problem = problem;
        //�Ƿ�ɽ�
        if (!local_problem.solvable()) {
            System.out.println("No Solution!");
            return null;
        }
        // ��ʼ�ڵ�root
        Node root = local_problem.root(predictor);
        //���̽�����
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

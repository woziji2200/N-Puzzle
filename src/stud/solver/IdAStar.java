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

    //�ü���ֵ
    private int cutoff;
    //��һ�ֵ����Ĳü���ֵ
    private int newCutoff;
    //���������
    private int maxIteratorDepth = 256;
    //ͳ����չ�����
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
        //��ȡ���ڵ�
        openStack.clear();
        //closeStack.clear();
        Node root = problem.root(predictor);
        cutoff = root.evaluation();

        while (cutoff < maxIteratorDepth) {
            openStack.push(root);
            newCutoff = cutoff;
            //��ջδ��ʱ������ִ�д��ü�ֵ�������������
            expanded = 0;
            while (!openStack.empty()) {
                expanded++;
                Node node = openStack.pop();
//                System.out.println("cost="+node.evaluation()+" pathCost="+node.getPathCost());
//                node.getState().draw();

                //���²ü�ֵΪδ��̽���ڵ�����С������ֵ
                if (problem.goal(node.getState())) {
//                    System.out.println("cutoff="+cutoff+" expanded="+expanded);
                    return generatePath(node);
                }
                //��С�ڵ��ڲü�ֵʱ�������������
                for (Node child : problem.childNodes(node, predictor)) {
                    //��֦����ֹ�ڵ�̽���ص����ڵ�
                    if (child.evaluation() <= cutoff) {
                        if (node.getParent() == null || !node.getParent().equals(child)) {

                            openStack.push(child);

                        }
                    } else {
                        //��¼���ڵ�ǰcutoff����Сֵ
                        newCutoff = (newCutoff > cutoff) ? (Math.min(child.evaluation(), newCutoff)) : child.evaluation();
                        //System.out.println("cutoff="+cutoff+" newcutoff="+newCutoff+" child.f="+child.evaluation());
                    }
                }
            }
            //���²ü�ֵ
            cutoff = newCutoff;
            //System.out.println("cutoff="+cutoff+" expanded="+expanded);
            //closeStack.clear();
            //System.out.println("cutoff: " + cutoff);
            //System.out.println("expanded node: " +  expanded);
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



///**
// * ���������A*�㷨����Ҫͬѧ���Լ���д���
// */
//public class IdAStar extends AbstractSearcher {
//    Predictor predictor;
//    private Set<State> explored = new HashSet<>();
//    private Set<State> expanded = new HashSet<>();
//    // ���ص�·��
//    Deque<Node> result = new ArrayDeque<>();
//    Problem local_problem;
//
//    public IdAStar(Frontier frontier, Predictor predictor) {
//        super(frontier); // Frontier�������������һ����ʹ�ö�ջ��ΪFrontier
//        this.predictor = predictor;
//    }
//    /**
//     *
//     * @param node  ��ǰ״̬
//     * @param parentNode ��һ״̬
//     * @param depth  ���̽�����
//     * @return
//     */
//    boolean depthFirstSearch(Node node, Node parentNode, int depth){
//        //��֦
//        if (node.getPathCost() >= depth)
//            return false;
//        if (local_problem.goal(node.getState())) {
//            //����ִ�Ŀ��״̬�����ݵõ�·��
//            result = generatePath(node);
//            return true;
//        }
//        //�����չ���
//        expanded.add(node.getState());
//        //�����ӽ��
//        List<Node> children = local_problem.childNodes(node, predictor);
//        //��ÿ���ӽ����е���
//        for (var child : children){
//            //������ɽ��
//            explored.add(child.getState());
//            //ȷ������ص���һ��
//            if (parentNode != null)
//                if(child.equals(parentNode))
//                    continue;
//            //С�����������������
//            if (child.evaluation() < depth && depthFirstSearch(child, node, depth)) {
//                return true;
//            }
//        }
//        return false;
//    }
//    @Override
//    public Deque<Node> search(Problem problem) {
//        local_problem = problem;
//        //�Ƿ�ɽ�
//        if (!local_problem.solvable()) {
//            System.out.println("No Solution!");
//            return null;
//        }
//        // ��ʼ�ڵ�root
//        Node root = local_problem.root(predictor);
//        //���̽�����
//        int depth = root.getHeuristic();
//        while (!depthFirstSearch(root, null, depth)) {
//            depth++;
//        }
//        return result;
//    }
//    @Override
//    public int nodesExpanded() {
//        return expanded.size();
//    }
//    @Override
//    public int nodesGenerated() {
//        return explored.size();
//    }
//}
package stud.queue;

import core.problem.State;
import core.solver.queue.Frontier;
import core.solver.queue.Node;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Description:
 *
 * @date:2022/10/12 9:41
 * @author:Karthus77
 */
public class MyFrontier extends PriorityQueue<Node> implements Frontier {
    //1. �Ƚ���
    private final Comparator<Node> evaluator;
    private final HashMap<Integer, Node> hashMap = new HashMap<>();

    public MyFrontier(Comparator<Node> evaluator) {
        super(evaluator);
        this.evaluator = evaluator;
    }

    @Override
    public Node poll(){
        Node node = super.poll();
        hashMap.remove((node.getState()).hashCode());
        return node;
    }

    private Node getNode(State state) {return hashMap.get(state.hashCode());}

    @Override
    public boolean contains(Node node) {
        return getNode(node.getState()) != null;
    }

    @Override
    public boolean offer(Node node) {
        Node oldNode = getNode(node.getState());
        if (oldNode == null) {
            super.offer(node);
            hashMap.put((node.getState()).hashCode(), node);
            return true;
        } else { //child�Ѿ���Frontier��
            return discardOrReplace(oldNode, node);
        }
    }

    //4. ����Ѿ���Frontier�У����ж������ɵĽڵ��Ƿ�Ӧ���滻
    private void replace(Node oldNode, Node newNode) {
        hashMap.put((oldNode.getState()).hashCode(),newNode);
        super.offer(newNode);
    }

    private boolean discardOrReplace(Node oldNode, Node node) {
        // ����ɽ��Ĺ�ֵ���µĴ󣬼������ɵĽ�����
        if (evaluator.compare(oldNode, node) > 0) {
            // ���½ڵ��滻�ɽڵ�
            replace(oldNode, node);
            return true;
        }
        return false;   //discard���ӵ��½��
    }

}
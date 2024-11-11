package stud.queue;

import core.problem.State;
import core.solver.queue.Frontier;
import core.solver.queue.Node;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class MyFrontier extends PriorityQueue<Node> implements Frontier {

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
        if (oldNode != null) { return discardOrReplace(oldNode, node); }
        super.offer(node);
        hashMap.put((node.getState()).hashCode(), node);
        return true;
    }

    private void replace(Node oldNode, Node newNode) {
        hashMap.put((oldNode.getState()).hashCode(),newNode);
        super.offer(newNode);
    }

    private boolean discardOrReplace(Node oldNode, Node node) {
        if (evaluator.compare(oldNode, node) > 0) {
            replace(oldNode, node);
            return true;
        }
        return false;
    }

}
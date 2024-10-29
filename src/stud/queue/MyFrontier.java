package stud.queue;
import core.problem.State;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;

import core.solver.queue.Frontier;
import core.solver.queue.Node;
import stud.problem.npuzzle.Position;

public class MyFrontier extends PriorityQueue<Node> implements Frontier {

    private Comparator<Node> comparator = new Comparator<Node>(){
        @Override
        public int compare(Node s1, Node s2) {
            return s1.compareTo(s2);
        }
    };

    PriorityQueue<Node> nodes = new PriorityQueue<Node>(comparator);
    private final HashMap<Integer,Node> map = new HashMap<Integer,Node>();
    protected final Comparator<Node> evaluator;
    public MyFrontier(Comparator<Node> evaluator) {
        this.evaluator = evaluator;
    }

    public Node getNode(State s) {
        return map.get(s.hashCode());
    }

    public boolean add(Node node) {
        nodes.add(node);
        map.put(node.getState().hashCode(), node);
        return true;
    }
    public Iterator<Node> iterator() {
        return nodes.iterator();
    }

    public int size() {
        return nodes.size();
    }

    public boolean offer(Node node) {
        return this.add(node);
    }

    public Node poll() {
        Node ans = nodes.poll();
        map.remove(((Position)(ans.getState())).getHashCode());
        return ans;
    }

    public Node peek() {
        return nodes.peek();
    }

    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    @Override
    public boolean contains(Node node) {
        return map.containsKey(node.getState().hashCode());
    }

    public void replace(Node from, Node to) {
        map.put(from.getState().hashCode(),to);
        nodes.add(to);
    }

    public void clear() {
        nodes.clear();
        map.clear();
    }
}
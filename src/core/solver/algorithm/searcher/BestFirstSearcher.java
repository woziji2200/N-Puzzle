package core.solver.algorithm.searcher;

import java.util.*;

import core.problem.Problem;
import core.solver.algorithm.heuristic.Predictor;
import core.solver.algorithm.searcher.AbstractSearcher;
import core.solver.queue.Frontier;
import core.solver.queue.Node;

/**
 * ������������㷨�� ���ݶ�Frontier��Node�����򷽷����Լ������õ����������Ĳ�ͬ �������ó���ͬ�����Ե��㷨��
 *  ǰ��������f(n) = g(n) + h(n)������h(n)���㵥������!  h(n)��0ʱ��Dijkstra�㷨��g(n)=0ʱ��Greedy Best-First
 * final�࣬���ܱ��̳е��ࡣͬѧ�ǲ��ɸ�д����࣡��
 *
 */
public final class BestFirstSearcher extends AbstractSearcher {

	private final Predictor predictor; //Ԥ�������Ե�ǰ״̬��������ʽ��ֵ

	/**
	 * ���캯��
	 *
	 * @param frontier Node�����һ�����ȶ��У�����ȷ��һ��״̬����Ӧ�Ľ���Ƿ���frontier�У�
	 * @param predictor �����Ԥ����������λ���ƣ������پ���ȣ�
	 */
	public BestFirstSearcher(Frontier frontier, Predictor predictor) {
		super(frontier);
		this.predictor = predictor;
	}

	@Override
	public Deque<Node> search(Problem problem) {
		// ���ж������Ƿ�ɽ⣬�޽�ʱֱ�ӷ��ؽ�·��Ϊnull
		if (!problem.solvable()) {
			return null;
		}

		// ÿ���µ�������ʼǰ���������Frontier��Explored������
		frontier.clear();
		explored.clear();
		nodesExpanded = 0;
		nodesGenerated = 0;

		// ��ʼ�ڵ�root
		Node root = problem.root(predictor);
		frontier.offer(root);

		// ����������
		while (true) {

			if (frontier.isEmpty())  // �ڵ���Ŀ��״̬֮ǰfrontier��Ϊ�գ��������޽�
				return null;

			Node node = frontier.poll(); // �����ȶ���frontier��ȡ����ֵ��С�Ľڵ�

			if (problem.goal(node.getState())) { //����Ŀ��״̬
				return generatePath(node);
			}

			explored.add(node.getState());

			//�Խڵ�node������չ  Expansion
			for (Node child : problem.childNodes(node, predictor)) {
				nodesGenerated++;
				if (!expanded(child)) // ��������ɵĽڵ㣨����չ���Ľڵ㣩��û�б���չ������뵽frontier�С�
					frontier.offer(child);
				// ����Ѿ���չ��������������
			}
			nodesExpanded++;
		}
	}
}
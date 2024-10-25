package core.runner;

import core.problem.Problem;
import core.solver.algorithm.searcher.AbstractSearcher;
import core.solver.algorithm.searcher.BestFirstSearcher;
import core.solver.queue.EvaluationType;
import core.solver.algorithm.heuristic.HeuristicType;
import core.solver.algorithm.heuristic.Predictor;
import core.solver.queue.Frontier;
import stud.queue.StackFrontier;
import stud.solver.IdAStar;

import java.util.ArrayList;

/**
 * Ϊ�����㷨�ṩ�����زġ�����
 *    ����ʵ���б�
 *    ʹ�õ�Frontier��
 *    ʹ�õ�����ʽ���� Predictor��
 *
 */
public abstract class EngineFeeder {
    /**
     * ���ݴ�����������������ı��ļ������ݣ���������ʵ���б�
     * @param problemLines  �ַ������飬��ŵ��ǣ����������������ı��ļ�������
     * @return
     */
    public abstract ArrayList<Problem> getProblems(ArrayList<String> problemLines);

    /**
     * ���ɲ�ȡĳ�ֹ�ֵ���Ƶ�Frontier���������޹أ�
     *
     * @param type ���������������
     * @return ʹ���������Ƶ�һ��Frontierʵ��
     */
    public abstract Frontier getFrontier(EvaluationType type);

    /**
     * ��ö�״̬���й�ֵ��Predictor����ͬ�����в�ͬ�Ĺ�ֵ����
     *
     * @param type ��ͬ����Ĺ�ֵ����������
     * @return ��������
     */
    public abstract Predictor getPredictor(HeuristicType type);

    /**
     * �������Ա�ʵ���IdAStar ��Iterative Deepening AStar�����������AStar��
     */
    public final AbstractSearcher getIdaStar(HeuristicType type) {
        Predictor predictor = getPredictor(type);
        // ��ȡFrontier����Node��g(n)+h(n)���������У���ͬʱ������g(n)����������
        Frontier frontier = new StackFrontier();
        //����IdAStar���棨�㷨ʵ����
        return new IdAStar(frontier, predictor);
    }

    /**
     * �������Ա�ʵ���AStar, ���������ⶼ��һ����
     * ������ʹ�ò�ͬ����������
     * @param type �����õ�������������
     */
    public final AbstractSearcher getAStar(HeuristicType type) {
        Predictor predictor = getPredictor(type);
        // ��ȡFrontier����Node��g(n)+h(n)���������У���ͬʱ������g(n)����������
        Frontier frontier = getFrontier(EvaluationType.FULL);
        // ����frontier��predictor����AStar����
        return new BestFirstSearcher(frontier, predictor);
    }

    /**
     * �������Ա�ʵ���Dijkstra�������е����ⶼ��һ����
     * 
     * @return Dijkstra�����㷨
     */
    public final AbstractSearcher getDijkstra() {
        // ��ȡFrontier����Node��g(n)����������
        Frontier frontier = getFrontier(EvaluationType.PATH_COST);
        // predictor��h(n)��0����Dijkstra�㷨
        return new BestFirstSearcher(frontier, (state, goal) -> 0);
    }
}

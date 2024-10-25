package stud.problem.pathfinding;

import core.problem.Action;
import core.problem.Problem;
import core.problem.State;
import core.solver.queue.Node;

import java.util.Deque;

/**
 * Ѱ·����
 */
public class PathFinding extends Problem {

    //��ͼ��Ϣ
    GridType[][] grids;
    public PathFinding(State initialState, State goal){
        this(initialState, goal, 0);
    }

    public PathFinding(State initialState, State goal, int size) {
        super(initialState, goal, size);
        grids = new GridType[size][size];
    }

    public void setGrids(GridType[][] grids) {
        for (int i = 0; i < size; i++){
            System.arraycopy(grids[i], 0, this.grids[i], 0, size);
        }
    }

    /**
     * ��ǰ�����Ƿ��н�
     * ��Ϊֻ��ͨ���������жϣ�������Ĭ��Ϊtrue
     * @return �н⣬true; �޽⣬false
     */
    @Override
    public boolean solvable() {
        return true;
    }

    /**
     * �ݵغ�ƽ�صĺ�ɢֵ��ͬ
     * @param state     ��ǰ״̬
     * @param action    ���뵱ǰ״̬����ȡ��Action
     * @return ���ǵ���״���ĺ�ɢֵ
     */
    @Override
    public int stepCost(State state, Action action) {
        Position position = (Position) state;
        GridType type = grids[position.getRow() - 1][position.getCol() - 1];
        return action.stepCost() * type.magnify();
    }

    @Override
    public boolean applicable(State state, Action action) {
        int[] offsets = Direction.offset(((Move)action).getDirection());
        int row = ((Position)state).getRow() + offsets[1];
        int col = ((Position)state).getCol() + offsets[0];
        return row > 0 && row <= size &&
               col > 0 && col <= size &&
               grids[row - 1][col - 1] != GridType.WALL;
    }

    @Override
    public void showSolution(Deque<Node> path) {
        //��ӡѰ·���⡣
        System.out.println(initialState + "��" + goal);
        //����ͼת��Ϊ�ַ����� 1->#; 2->*
        char[][] grids = new char[size][];
        for (int i = 0; i < size; i++){
            grids[i] = new char[size];
            for (int j = 0; j < size; j++) {
                grids[i][j] = this.grids[i][j].symbol();
            }
        }

        //������
        int row = ((Position)initialState).getRow();
        int col = ((Position)initialState).getCol();
        grids[row - 1][col - 1] = '@';
        //���յ�
        row = ((Position)goal).getRow();
        col = ((Position)goal).getCol();
        grids[row - 1][col - 1] = '��';

        //����·���еĶ�������д���ַ�����grids
        for (Node node : path) {
            Position p = (Position) node.getParent().getState();
            Move move = (Move) node.getAction();
            Direction d = move.getDirection();
            grids[p.getRow() - 1][p.getCol() - 1] = d.symbol();
        }

        //��ӡ�ַ�����
        drawGrid(grids);
    }

    private void drawGrid(char[][] grids) {
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                System.out.print(grids[i][j] + " ");
            }
            System.out.println();
        }
    }

}

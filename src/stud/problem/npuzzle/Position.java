package stud.problem.npuzzle;

import core.problem.Action;
import core.problem.State;

public class Position extends State {
    public int[][] state;  // ��ǰ���̾����״̬
    public int[] zeroPosition = new int[2]; // �ո��λ��

    public Position(int[][] state) {
        this.state = state;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] == 0) {
                    this.zeroPosition[0] = i;
                    this.zeroPosition[1] = j;
                }
            }
        }
    }

    /**
     * ������Ŀո������ƶ����ɹ�����true��ʧ�ܷ���false
     *
     * @return �Ƿ�ɹ�
     */
    public boolean goUp() {
        if (zeroPosition[0] == 0) {
            return false;
        }
        state[zeroPosition[0]][zeroPosition[1]] = state[zeroPosition[0] - 1][zeroPosition[1]];
        state[zeroPosition[0] - 1][zeroPosition[1]] = 0;
        zeroPosition[0]--;
        return true;
    }

    /**
     * ������Ŀո������ƶ����ɹ�����true��ʧ�ܷ���false
     *
     * @return �Ƿ�ɹ�
     */
    public boolean goDown() {
        if (zeroPosition[0] == state.length - 1) {
            return false;
        }
        state[zeroPosition[0]][zeroPosition[1]] = state[zeroPosition[0] + 1][zeroPosition[1]];
        state[zeroPosition[0] + 1][zeroPosition[1]] = 0;
        zeroPosition[0]++;
        return true;
    }

    /**
     * ������Ŀո������ƶ����ɹ�����true��ʧ�ܷ���false
     *
     * @return �Ƿ�ɹ�
     */
    public boolean goLeft() {
        if (zeroPosition[1] == 0) {
            return false;
        }
        state[zeroPosition[0]][zeroPosition[1]] = state[zeroPosition[0]][zeroPosition[1] - 1];
        state[zeroPosition[0]][zeroPosition[1] - 1] = 0;
        zeroPosition[1]--;
        return true;
    }

    /**
     * ������Ŀո������ƶ����ɹ�����true��ʧ�ܷ���false
     *
     * @return �Ƿ�ɹ�
     */
    public boolean goRight() {
        if (zeroPosition[1] == state.length - 1) {
            return false;
        }
        state[zeroPosition[0]][zeroPosition[1]] = state[zeroPosition[0]][zeroPosition[1] + 1];
        state[zeroPosition[0]][zeroPosition[1] + 1] = 0;
        zeroPosition[1]++;
        return true;
    }

    @Override
    public void draw() {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                System.out.print(state[i][j] + " ");
            }
            System.out.print('\n');
        }
        System.out.print('\n');
    }

    @Override
    public State next(Action action) {
        int[][] nextStateArray = new int[state.length][state[0].length];
        for (int i = 0; i < state.length; i++) {
            System.arraycopy(state[i], 0, nextStateArray[i], 0, state[i].length);
        }
        Position nextState = new Position(nextStateArray);


        return nextState;
    }

    @Override
    public Iterable<? extends Action> actions() {
        return null;
    }
}

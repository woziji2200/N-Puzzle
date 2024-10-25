package stud.problem.npuzzle;

import core.problem.Action;
import core.problem.State;

public class Position extends State {
    private int[][] state;  // 当前棋盘矩阵的状态
    private int size;       // 棋盘矩阵的大小
    public void setState(int[][] state){
        this.state = state;
    }
    public void setSize(int size){
        this.size = size;
    }
    @Override
    public void draw() {
        for(int i=0;i<state.length;i++){
            for(int j = 0; j < state[i].length; j++){
                System.out.print(state[i][j] + " ");
            }
            System.out.print('\n');
        }
        System.out.print('\n');
    }

    @Override
    public State next(Action action) {
        return null;
    }

    @Override
    public Iterable<? extends Action> actions() {
        return null;
    }
}

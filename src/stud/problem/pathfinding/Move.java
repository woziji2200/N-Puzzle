package stud.problem.pathfinding;

import core.problem.Action;

/**
 *
 * Ѱ·���ƶ��������������������NSEW�ĸ����򣬻���8������
 */
public class Move extends Action {

    private final Direction direction;

    public Move(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void draw() {
        System.out.println(this);
    }

    @Override
    public int stepCost() {
        return Direction.cost(direction);
    }

    @Override
    public String toString() {
        return direction.name();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj instanceof Move) {
            Move another = (Move) obj;
            //����Node�����״̬��ͬ������Ϊ����ͬ��
            return this.direction.equals(another.direction);
        }
        return false;
    }
}

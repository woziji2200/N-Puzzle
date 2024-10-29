package stud.problem.npuzzle;

import core.problem.Action;

public class Move extends Action {

    public Direction direction;
    Move(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void draw() {
        System.out.println("   ¡ý");
        System.out.println("   ¡ý-(#, " + direction + ")");
        System.out.println("   ¡ý");
    }

    @Override
    public int stepCost() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Move) {
            return this.direction == ((Move) obj).direction;
        }
        return false;
    }

}

package stud.problem.npuzzle;

public enum Direction {
    UP('N'), DOWN('S'), LEFT('W'), RIGHT('E');
    private char direction;
    Direction(char e) {
        this.direction = e;
    }
}

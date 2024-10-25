package stud.problem.pathfinding;

public enum GridType {
    EMPTY('0', 1),  // �յ�
    GRASS('*', 5),  // �ݵأ�ͨ���Ĵ��۸ߣ���ͨ���۵�5��
    //MUDDY('~', 5),  // ��أ�ͨ������Ϊ7��
    WALL('+', Integer.MAX_VALUE);   // ʯǽ���޷�ͨ��

    private final char symbol; 
    private final int magnify;

    GridType(char symbol, int magnify){
        this.symbol = symbol;
        this.magnify = magnify;
    }

    public char symbol(){
        return symbol;
    }
    public int magnify(){return magnify;}

    public GridType valueOfDigit(char digit){
        return values()[digit - '0'];
    }

    @Override
    public String toString() {
        return symbol + "";
    }
}

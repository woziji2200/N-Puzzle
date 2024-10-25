package stud.problem.pathfinding;

import java.util.EnumMap;
import java.util.List;

/**
 * 
 * ��ͼ�п����ƶ���8�����򣬼����ͷ����
 */
public enum Direction {
    N('��'),  //��
    NE('�J'), //����
    E('��'),  //��
    SE('�K'), //����
    S('��'),  //��
    SW('�L'), //����
    W('��'),  //��
    NW('�I'); //����

    private final char symbol;

    /**
     * ���캯��
     * @param symbol ö����Ĵ������--��ͷ
     */
    Direction(char symbol){
        this.symbol = symbol;
    }

    public char symbol(){
        return symbol;
    }

    public static final int SCALE = 10;       //��Ԫ��ı߳�
    public static final double ROOT2 = 1.4;   //2��ƽ����

    /**
     * �ƶ���������ֲ�ͬ�����4������8�����򣩡�
     */
    public static final List<Direction> FOUR_DIRECTIONS = List.of(Direction.N, Direction.E, Direction.S, Direction.W);
    public static final List<Direction> EIGHT_DIRECTIONS = List.of(Direction.values());
    
    /**
     * ��ͬ����ĺ�ɢֵ
     */
    public static int cost(Direction dir){
        return FOUR_DIRECTIONS.contains(dir) ? SCALE : (int) (SCALE * ROOT2);
    }

    //���������ƶ�������λ����
    private static final EnumMap<Direction, int[]> DIRECTION_OFFSET = new EnumMap<>(Direction.class);
    static{
        //�кţ�������꣩���������кţ��������꣩������
        DIRECTION_OFFSET.put(N, new int[]{0, -1});
        DIRECTION_OFFSET.put(NE, new int[]{1, -1});
        DIRECTION_OFFSET.put(E, new int[]{1, 0});
        DIRECTION_OFFSET.put(SE, new int[]{1, 1});
        DIRECTION_OFFSET.put(S, new int[]{0, 1});
        DIRECTION_OFFSET.put(SW, new int[]{-1, 1});
        DIRECTION_OFFSET.put(W, new int[]{-1, 0});
        DIRECTION_OFFSET.put(NW, new int[]{-1, -1});
    }
    
    public static int[] offset(Direction dir){
        return DIRECTION_OFFSET.get(dir);
    }
}

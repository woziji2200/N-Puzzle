package stud.problem.npuzzle;

import core.problem.Action;
import core.problem.State;

import java.util.Collection;
import java.util.Random;


/**
 * 我们认为Position创建以后，棋盘的state就不会再改变了。所以hashCode和zeroPosition只需要计算一次
 */
public class Position extends State {
    private int[][] state;  // 当前棋盘矩阵的状态
    private int[] zeroPosition = {-1, -1}; // 空格的位置
    private int size;
    public int[][] getState() {
        return state;
    }
    public int[] getZeroPosition() {
        if(zeroPosition[0] != -1 && zeroPosition[1] != -1) {
            return zeroPosition;
        }
        // 只有此前没有计算过zeroPosition时才计算
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                if (state[i][j] == 0) {
                    this.zeroPosition[0] = i;
                    this.zeroPosition[1] = j;
                }
            }
        }
        return zeroPosition;
    }
    public int getSize() {
        return size;
    }

    public static int[][] zobristTable; // Random hash values
    public static boolean hasZobristTable = false;
    private static void generateZobristTable() {
//        Random random = new Random();
//        zobristTable = new int[20][20];
//        for (int i = 0; i < 20; i++) {
//            for (int j = 0; j < 20; j++) {
//                zobristTable[i][j] = random.nextInt();
//            }
//        }
//        for (int i = 0; i < 20; i++) {
//            System.out.print("{");
//            for (int j = 0; j < 20; j++) {
//                System.out.print(zobristTable[i][j] + ",");
//            }
//            System.out.println("}");
//        }


        // 生成一次后就采用固定的值
        zobristTable = new int[][]{
                {-1850780436,-1585926736,-357705709,-1633547965,-1290721715,-1165629366,1319310366,-1512568217,-1848516811,-515070516,773334446,-879916493,1139127954,126836209,775838550,1865834405,-468376027,-861491633,-2037729481,755933901},
                {-1974936695,403738899,12972454,-293919584,1068840307,-1792029967,-1689812468,-245821429,2095698234,1186972757,-1168256498,-864155573,-49419110,-804240930,-1587282195,-1981065093,383426484,459255820,1227345198,1548486766},
                {1433416536,-1763576265,-1907838339,-1770067266,959715207,2020410562,962795982,241891365,532233779,-509373807,174671300,963480587,-1718821304,-1626751561,-625117866,764892767,2044584687,-889814156,481767087,1894127273},
                {-1097495792,1158693726,-1518873514,-1678347168,-472122087,-1892564515,1435560079,1636468856,611046142,-864526885,272671488,1828121663,-1396448640,-569152068,-1775256426,-1202626199,-2106145360,-1502087284,-2115660375,-983996567},
                {919332790,-2045036425,1876302429,1791250945,-723641906,269070205,-1942025908,1612428572,-2086669165,-1747476635,2084601953,636893659,1005488512,-1597817870,670495750,1950363885,-1369495374,-558893484,-15010687,-1379240413},
                {-67289358,-1742365471,990733723,2091612482,1962512371,-202030588,-1770189694,1197965339,-392408873,1892753013,1312593904,-1869449767,-1226840329,1078190762,-1095858549,183425945,1311919843,71773068,-140603505,-1875726785},
                {-617123929,-159379858,1941336489,-1579919993,-45687216,1965289530,724978726,1724837978,1694750126,1928797165,1232528972,-2006019486,1930847834,687459396,1413384456,136988384,-1745280262,1886362807,-380154168,1654320316},
                {993971395,1446548902,-731902550,-1661255715,-186137537,875710418,46088150,1418634171,-1902689712,231385870,1211207207,-2028895439,-2131452556,693482549,1642324240,-1708172905,-477814967,861001310,-1163715651,-2094548720},
                {1057959507,1658250062,246015352,-930291169,-1671442435,-1976697042,-1019796146,865845376,-11024675,591960957,194909170,1045848373,1085657404,-659051193,1697318880,-863554082,-1402483756,-668757354,-1958322235,1231337805},
                {-1232814885,643324713,1743859485,1861372249,-1968305344,-301897629,944607517,1890863602,-1061788108,1147836550,-878787116,-31791754,1917919467,-2108919520,2068544908,594017858,-1602943325,621542597,-298912138,-552983158},
                {-397808923,1043222525,2133577861,-961565843,388528650,-28644992,-256001127,535960643,531107328,-1782095456,326460384,-1112817863,791579469,-614851322,-300138375,-99751392,1479707220,1039726240,1911826521,-1884799359},
                {564154583,146019545,-1637822094,2113807348,-1009360963,-364093941,1535614481,-1035398223,1740019988,-1151844303,357850247,2145093991,412435032,1270923233,-166208245,1451339084,-2036373771,1854669432,1675592159,-2055599243},
                {954621706,-662059114,1408445872,410119271,1825996399,-1228306995,114897986,-1902763283,-390385671,470359700,-1827262132,461529899,1213399292,-1278845567,936962464,199653107,1503289616,764196535,287349390,-849747114},
                {-1793320424,-558106468,1726897034,1061933950,1954727040,1939596495,1190688268,59137211,-1005143099,-1386486695,1583404983,-1936101413,747132729,148720417,2103313085,1223887805,1109289736,1696194213,-740810623,-139492056},
                {-782412427,-287337497,-1309533571,2038822872,-675603351,-2051688284,1955650917,702690522,1295605550,-1169228853,-1301206388,1803469337,1376133239,1697581356,-864117881,-572080329,1976609955,-1956533136,-1440433137,-9387103},
                {630202434,-225661111,-1031971285,2126580189,474829527,-192616727,466455694,1894939685,1146759647,404890807,-973816003,460027449,1417451538,-392888768,-2004339084,727470193,-1338782488,-516120469,881762250,-1909258223},
                {2141345886,-383114767,-764515949,-1221551904,1796228857,1516374283,-253532027,-603857925,61110795,1959000172,-1074351203,-1912143898,1193590836,512472945,-1656890813,1561456099,998273579,601754006,-480978772,-1005244281},
                {-1058957378,-1492711124,1876455039,901918601,440195351,-80283320,-1889748037,2142164189,2098774080,-1092238594,540428247,59240192,768151272,953455055,1906956074,676783253,1038298214,-299351201,-8801651,1731628337},
                {729608190,1217306218,-536001218,272384882,555174755,1275823352,-405129978,1125614678,-198534787,-1674930736,-444311394,473673673,-1016949396,-349900502,1369190478,411876991,167001547,-632154969,-1590049577,-1748958972},
                {1935157304,-46792059,-2043932525,-298370032,-967309036,921577486,-1739368742,-215894497,-133794937,-538855599,-1673589338,-1931736511,992220614,-827234284,831405433,-1538599890,-1897532008,2125321224,-1575257219,-1367242339}
        };
    }

    /**
     * 生成一个状态
     * @param state 棋盘状态
     */
    public Position(int[][] state) {
        if (!hasZobristTable) {
            generateZobristTable();
//            System.out.println("生成ZobristTable");
            hasZobristTable = true;
        }
        this.state = state;
        this.size = state.length;
    }

    /**
     * 生成一个状态，同时指定空格的位置，减少计算空格位置的时间
     * @param state
     * @param zeroPosition2
     */
    public Position(int[][] state, int[] zeroPosition2) {
        if (!hasZobristTable) {
            generateZobristTable();
//            System.out.println("生成ZobristTable");
            hasZobristTable = true;
        }
        this.state = state;
        this.size = state.length;
        this.zeroPosition = zeroPosition2;
    }

    /**
     * 生成一个状态，并且不需要计算空格的位置；同时指定不相交模式的哈希值
     * @param state
     * @param noZeroPosition
     * @param _disjointPatternHashCode
     */
    public Position(int[][ ]state, boolean noZeroPosition, int _disjointPatternHashCode) {
        if (!hasZobristTable) {
            generateZobristTable();
//            System.out.println("生成ZobristTable");
            hasZobristTable = true;
        }
        this.state = state;
        this.size = state.length;
        this.disjointPatternHashCode = _disjointPatternHashCode;
    }

    /**
     * 生成一个状态，并且不需要计算空格的位置；同时指定哈希值
     * @param state
     * @param zeroPosition
     * @param _hashCode
     */
    public Position(int[][] state, int[] zeroPosition, int _hashCode) {
        if (!hasZobristTable) {
            generateZobristTable();
        }
        this.state = state;
        this.size = state.length;
        this.zeroPosition = zeroPosition;
        this.zHashCode = _hashCode;
    }



    @Override
    public void draw() {
        System.out.print("-----\n");
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                System.out.print(state[i][j] + " ");
            }
            System.out.print('\n');
        }
        System.out.print("-----\n");
    }

    @Override
    public State next(Action action) {
        // 复制当前状态
        int[][] nextStateArray = new int[getSize()][getSize()];
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                nextStateArray[i][j] = state[i][j];
            }
        }

        if (action instanceof Move) {
            Move move = (Move) action;

            int size = getSize();
            int x = getZeroPosition()[0];   // 当前空格的x位置
            int y = getZeroPosition()[1];   // 当前空格的y位置
            int[] zeroPosition2 = {x, y};
            int nextHashCode = getZHashCode();  // 下一个状态的哈希值
            switch (move.direction) {
                case UP:
                    if (x > 0) {
                        // 我们直接根据当前状态的哈希值计算下一个状态的哈希值，大幅减少了计算时间
                        nextHashCode ^= zobristTable[nextStateArray[x - 1][y]][(x - 1) * size + y];
                        nextStateArray[x][y] = nextStateArray[x - 1][y];
                        nextStateArray[x - 1][y] = 0;
                        zeroPosition2[0] = x - 1;
                        nextHashCode ^= zobristTable[nextStateArray[x][y]][(x) * size + y];
                    }
                    break;
                case DOWN:
                    if (x < size - 1) {
                        nextHashCode ^= zobristTable[nextStateArray[x + 1][y]][(x + 1) * size + y];
                        nextStateArray[x][y] = nextStateArray[x + 1][y];
                        nextStateArray[x + 1][y] = 0;
                        zeroPosition2[0] = x + 1;
                        nextHashCode ^= zobristTable[nextStateArray[x][y]][(x) * size + y];
                    }
                    break;
                case LEFT:
                    if (y > 0) {
                        nextHashCode ^= zobristTable[nextStateArray[x][y - 1]][(x) * size + y - 1];
                        nextStateArray[x][y] = nextStateArray[x][y - 1];
                        nextStateArray[x][y - 1] = 0;
                        zeroPosition2[1] = y - 1;
                        nextHashCode ^= zobristTable[nextStateArray[x][y]][x * size + y];
                    }
                    break;
                case RIGHT:
                    if (y < size - 1) {
                        nextHashCode ^= zobristTable[nextStateArray[x][y + 1]][(x) * size + y + 1];
                        nextStateArray[x][y] = nextStateArray[x][y + 1];
                        nextStateArray[x][y + 1] = 0;
                        zeroPosition2[1] = y + 1;
                        nextHashCode ^= zobristTable[nextStateArray[x][y]][x * size + y];
                    }
                    break;
                default:
                    break;
            }
            Position nextState = new Position(nextStateArray, zeroPosition2, nextHashCode);
            return nextState;
        }
        return null;

    }

    @Override
    public Iterable<? extends Action> actions() {
        Collection<Action> actions = new java.util.ArrayList<>();
        actions.add(new Move(Direction.UP));
        actions.add(new Move(Direction.DOWN));
        actions.add(new Move(Direction.LEFT));
        actions.add(new Move(Direction.RIGHT));
        return actions;
    }

    @Override
    public int hashCode() {
        return getZHashCode();
    }

    private int zHashCode = 0;
    // 计算Zobrist哈希值
    public int getZHashCode() {
        if(zHashCode == 0) {
            int hash = 0;
            int disjHash = 0;
            for (int i = 0; i < getSize(); i++) {
                for (int j = 0; j < getSize(); j++) {
                    int piece = state[i][j];
                    if (piece != 0) { // 0表示空格
                        hash ^= (zobristTable[piece][i * getSize() + j]);
                    }
                }
            }
            zHashCode = hash;

        }
        return zHashCode;
    }

    private int disjointPatternHashCode = 0;
    // 计算不相交模式的哈希值，忽略了16和0
    public int getDisjointPatternHashCode() {
        if(disjointPatternHashCode == 0) {
            int hash = 0;
            for (int i = 0; i < getSize(); i++) {
                for (int j = 0; j < getSize(); j++) {
                    int piece = state[i][j];
                    if (piece != 0 && piece != 16) {
                        hash ^= (zobristTable[piece][i * getSize() + j]);
                    }
                }
            }
            disjointPatternHashCode = hash;
        }
        return disjointPatternHashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position other = (Position) obj;
            boolean flag = true;
            if(getSize() != other.getSize() || getSize() != other.getSize()) {
                return false;
            }
            return hashCode() == other.hashCode();
        }
        return false;
    }
}

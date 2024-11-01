package stud.problem.npuzzle;

import core.problem.Action;
import stud.problem.npuzzle.distance.Manhattan;

import java.util.*;

public class Database {

    public static void main(String[] args){
//        HashMap<Integer, Integer> db1 = getDatabase(new Position(new int[][]{{1, 2, 3, 4}, {5, 6, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}}));
//        System.out.println(db1);
    }
    
    public static HashMap<Integer, Integer> getDatabase(Position pattern) {
        HashMap<Integer, Integer> database = new HashMap<>();
        // bfs to search all states and store the distance to the goal state
        int count = 0;
        Queue<PositionWithDistance> queue = new LinkedList<>();
        HashSet<Integer> visited = new HashSet<>();
        queue.add(new PositionWithDistance(pattern, 0));
//        visited.add(pattern.hashCode());
        database.put(pattern.hashCode(), 0);
        while (!queue.isEmpty()) {
            PositionWithDistance currentPWD = queue.poll();
            Position current = currentPWD.position;
            int currentDistance = currentPWD.distance;
//            if(visited.contains(current.hashCode())) continue;
            int [][] currentState = current.getState();
            for(int i = 0; i < current.getSize();i++){
                for(int j = 0; j < current.getSize(); j++){
                    if(currentState[i][j] == 0) continue;
                    // 判断可否移动
                    // 向上，要求不在第一行，且上面的位置为0
                    if(i > 0 && currentState[i-1][j] == 0){
                        int[][] newState = new int[current.getSize()][current.getSize()];
                        for(int k = 0; k < current.getSize(); k++){
                            newState[k] = Arrays.copyOf(currentState[k], current.getSize());
                        }
                        newState[i-1][j] = currentState[i][j];
                        newState[i][j] = 0;
                        Position newPosition = new Position(newState);
                        if(!visited.contains(newPosition.hashCode())){
                            queue.add(new PositionWithDistance(newPosition, currentDistance+1));
                            visited.add(newPosition.hashCode());
                            database.put(newPosition.hashCode(), currentDistance+1);
                            count++;
                        }
                    }

                    // 向下，要求不在最后一行，且下面的位置为0
                    if(i < current.getSize()-1 && currentState[i+1][j] == 0){
                        int[][] newState = new int[current.getSize()][current.getSize()];
                        for(int k = 0; k < current.getSize(); k++){
                            newState[k] = Arrays.copyOf(currentState[k], current.getSize());
                        }
                        newState[i+1][j] = currentState[i][j];
                        newState[i][j] = 0;
                        Position newPosition = new Position(newState);
                        if(!visited.contains(newPosition.hashCode())){
                            queue.add(new PositionWithDistance(newPosition, currentDistance+1));
                            visited.add(newPosition.hashCode());
                            count++;
                            database.put(newPosition.hashCode(), currentDistance+1);
                        }
                    }

                    // 向左，要求不在第一列，且左边的位置为0
                    if(j > 0 && currentState[i][j-1] == 0){
                        int[][] newState = new int[current.getSize()][current.getSize()];
                        for(int k = 0; k < current.getSize(); k++){
                            newState[k] = Arrays.copyOf(currentState[k], current.getSize());
                        }
                        newState[i][j-1] = currentState[i][j];
                        newState[i][j] = 0;
                        Position newPosition = new Position(newState);
                        if(!visited.contains(newPosition.hashCode())){
                            queue.add(new PositionWithDistance(newPosition, currentDistance+1));
                            visited.add(newPosition.hashCode());
                            count++;
                            database.put(newPosition.hashCode(), currentDistance+1);
                        }
                    }

                    // 向右，要求不在最后一列，且右边的位置为0
                    if(j < current.getSize()-1 && currentState[i][j+1] == 0){
                        int[][] newState = new int[current.getSize()][current.getSize()];
                        for(int k = 0; k < current.getSize(); k++){
                            newState[k] = Arrays.copyOf(currentState[k], current.getSize());
                        }
                        newState[i][j+1] = currentState[i][j];
                        newState[i][j] = 0;
                        Position newPosition = new Position(newState);
                        if(!visited.contains(newPosition.hashCode())){
                            queue.add(new PositionWithDistance(newPosition, currentDistance+1));
                            visited.add(newPosition.hashCode());
                            count++;
                            database.put(newPosition.hashCode(), currentDistance+1);
                        }
                    }
                }
            }

        }



        System.out.println("count = " + count);
        return database;
    }


}

class PositionWithDistance{
    public Position position;
    public int distance;
    public PositionWithDistance(Position position, int distance){
        this.position = position;
        this.distance = distance;
    }
}

package stud.problem.npuzzle;

import core.problem.Action;
import stud.problem.npuzzle.distance.Manhattan;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

public class Database {

    public static void main(String[] args){
//        HashMap<Integer, Integer> db1 = getDatabase(new Position(new int[][]{{1, 2, 3, 4}, {5, 6, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}}));
//        System.out.println(db1);
    }
    
    public static HashMap<Integer, Integer> getDatabase(Position pattern, String filename) {
        File file = new File(filename);
        if(file.exists()){
            return loadDatabase(filename);
        }


        HashMap<Integer, Integer> database = new HashMap<>();
        // bfs to search all states and store the distance to the goal state
        int count = 0;
        int count2 = 0;
        Queue<Position> queue = new LinkedList<>();
        HashMap<Integer, Integer> visited = new HashMap<>();
        queue.add(pattern);
        visited.put(pattern.hashCode(), 0);
        //        visited.add(pattern.hashCode());
        database.put(pattern.getDisjointPatternHashCode(), 0);
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            int currentDistance = visited.get(current.hashCode());
            int [][] currentState = current.getState();
            for(int i = 0; i < current.getSize();i++){
                for(int j = 0; j < current.getSize(); j++){
                    if(currentState[i][j] == 0) continue;
                    // 定义方向数组
                    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 上, 下, 左, 右

                    for (int[] direction : directions) {
                        int newX = i + direction[0];
                        int newY = j + direction[1];

                        // 检查移动是否合法（是否在边界内并且目标位置为空）
                        if (newX >= 0 && newX < current.getSize() && newY >= 0 && newY < current.getSize() && currentState[newX][newY] == 0) {
                            // 创建新状态
                            int[][] newState = new int[current.getSize()][current.getSize()];
                            for (int k = 0; k < current.getSize(); k++) {
                                newState[k] = Arrays.copyOf(currentState[k], current.getSize());
                            }
                            // 移动位置
                            newState[newX][newY] = currentState[i][j];
                            newState[i][j] = 0;
                            Position newPosition = new Position(newState);

                            boolean thisPatternVisited = visited.containsKey(newPosition.hashCode());
                            boolean thisLikedPatternVisited = database.containsKey(newPosition.getDisjointPatternHashCode());
                            int nextDistance = newState[newX][newY] == 16 ? currentDistance : currentDistance + 1;

                            if(!thisPatternVisited){
                                queue.add(newPosition);
                                count++;
                                visited.put(newPosition.hashCode(), nextDistance);
                                if(database.getOrDefault(newPosition.getDisjointPatternHashCode(), 99999) >= nextDistance){
                                    if(thisLikedPatternVisited){
                                        database.replace(newPosition.getDisjointPatternHashCode(), nextDistance);
                                    } else {
                                        database.put(newPosition.getDisjointPatternHashCode(), nextDistance);
                                        count2++;
                                    }
                                }
                            }
//                            if(newState[newX][newY] == 16){
//
//                            } else {
//                                if(!visited.containsKey(newPosition.hashCode())){
//                                    count++;
//                                    // 更新队列和数据库
//                                    queue.add(new Position(newState));
//                                    visited.put(newPosition.hashCode(), currentDistance+1);
//                                    database.put(newPosition.getDisjointPatternHashCode(), currentDistance + 1);
//                                } else if(visited.containsKey(newPosition.hashCode()) && database.get(newPosition.getDisjointPatternHashCode()) > currentDistance + 1){
//                                    count++;
//                                    queue.add(new Position(newState));
//                                    visited.put(newPosition.hashCode(), currentDistance+1);
//                                    database.put(newPosition.getDisjointPatternHashCode(), currentDistance+1);
//                                }
//                            }

                        }
                    }

                }
            }

        }

        System.out.println("count = " + count);
        System.out.println("count2 = " + count2);
        saveDatabase(database, filename);
        return database;
    }

    public static void saveDatabase(HashMap<Integer, Integer> database, String filename) {
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
            out.writeInt(database.size());  // 写入大小
            for (var entry : database.entrySet()) {
                out.writeInt(entry.getKey());
                out.writeInt(entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Integer, Integer> loadDatabase(String filename) {
        HashMap<Integer, Integer> database = new HashMap<>();
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
            int size = in.readInt();
            for (int i = 0; i < size; i++) {
                int key = in.readInt();
                int value = in.readInt();
                database.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return database;
    }
//    public static void saveDatabase(HashMap<Integer, Integer> map, String filename) {
//        // 计算文件大小，每个键值对占用 8 字节（4字节的 key + 4字节的 value）
//        int fileSize = map.size() * 8;
//
//        try (RandomAccessFile file = new RandomAccessFile(filename, "rw");
//             FileChannel channel = file.getChannel()) {
//            // 将文件大小设为所需的字节数
//            file.setLength(fileSize);
//            // 创建 MappedByteBuffer
//            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, fileSize);
//            // 写入每个键值对
//            for (HashMap.Entry<Integer, Integer> entry : map.entrySet()) {
//                buffer.putInt(entry.getKey());
//                buffer.putInt(entry.getValue());
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    // 从 Memory-Mapped File 中加载数据到 HashMap
//    public static HashMap<Integer, Integer> loadDatabase(String filename){
//        HashMap<Integer, Integer> map = new HashMap<>();
//
//        File file = new File(filename);
//        try (RandomAccessFile raf = new RandomAccessFile(file, "r");
//             FileChannel channel = raf.getChannel()) {
//            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
//
//            // 读取键值对并存入 HashMap
//            while (buffer.hasRemaining()) {
//                int key = buffer.getInt();
//                int value = buffer.getInt();
//                map.put(key, value);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        return map;
//    }
}

class PositionWithDistance{
    public Position position;
    public int distance;
    public PositionWithDistance(Position position, int distance){
        this.position = position;
        this.distance = distance;
    }
}

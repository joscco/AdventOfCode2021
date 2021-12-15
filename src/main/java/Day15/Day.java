package main.java.Day15;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;
import main.java.Day5.Tuple;

import java.util.*;

public class Day extends AbstractDay {

    int height;
    int width;
    int[][] nodes;

    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {
        List<String> lines = InputUtils.readInStringLines(getInputStream());
        height = lines.size();
        width = lines.get(0).length();
        nodes = buildNodes(lines);
        solveFirstQuestion();
        solveSecondQuestion();
    }

    private int[][] buildNodes(List<String> lines) {
        int[][] nodes = new int[width][height];
        for (int y = 0; y < height; y++) {
            int[] values = Arrays.stream(lines.get(y).split("")).mapToInt(Integer::parseInt).toArray();
            for (int x = 0; x < width; x++) {
                nodes[x][y] = values[x];
            }
        }
        return nodes;
    }

    private void solveFirstQuestion() {
        long shortestDistance = findShortestBetween(new Tuple<>(0, 0), new Tuple<>(width - 1, height - 1));
        System.out.println("Part 1:");
        System.out.println(shortestDistance);
    }

    private void solveSecondQuestion() {
        nodes = enlargeNodes(nodes);
        width *= 5;
        height *= 5;
        long shortestDistance = findShortestBetween(new Tuple<>(0, 0), new Tuple<>(width - 1, height - 1));
        System.out.println("Part 2:");
        System.out.println(shortestDistance);
    }

    private int[][] enlargeNodes(int[][] nodes) {
        int[][] newNodes = new int[5*width][5*height];
        for (int y = 0; y<height; y++) {
            for(int x = 0; x<width;x++) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        newNodes[i * width + x][j * height + y] = wrap(nodes[x][y] + i + j);
                    }
                }
            }
        }
        return newNodes;
    }

    private long findShortestBetween(Tuple<Integer, Integer> start, Tuple<Integer, Integer> end) {

        long[][] distances = new long[width][height];
        ComparatorByValue comparator = new ComparatorByValue(distances);
        PriorityQueue<Tuple<Integer, Integer>> unvisited = new PriorityQueue<>(width*height, comparator);
        for (int x= 0; x<width; x++) {
            for(int y = 0; y<height;y++) {
                distances[x][y] = Integer.MAX_VALUE;
                unvisited.add(new Tuple<>(x,y));
            }
        }

        distances[start._0][start._1] = 0L;
        Long timeStart = System.currentTimeMillis();

        while (!unvisited.isEmpty()) {
            Tuple<Integer, Integer> nearestTuple = unvisited.poll();
            if (end.equals(nearestTuple)) {
                Long timeEnd = System.currentTimeMillis();
                System.out.println("Time passed: " + (timeEnd - timeStart) + "ms");
                return distances[nearestTuple._0][nearestTuple._1];
            }

            updateDistanceViaNeightbours(nearestTuple, unvisited, distances);
        }
        return -1;
    }

    private void updateDistanceViaNeightbours(Tuple<Integer, Integer> nearestTuple, PriorityQueue<Tuple<Integer, Integer>> unvisited, long[][] distances) {
        long nearestDist = distances[nearestTuple._0][nearestTuple._1];
        for (Tuple<Integer, Integer> currentVertex : getAdjacents(nearestTuple, width, height)) {
            long temp = nearestDist + nodes[currentVertex._0][currentVertex._1];
            if (temp < distances[currentVertex._0][currentVertex._1]) {
                distances[currentVertex._0][currentVertex._1] =  temp;
                // We can just add the same vertex again with another (higher) priority
                unvisited.add(currentVertex);
            }
        }
    }

    private List<Tuple<Integer, Integer>> getAdjacents(Tuple<Integer, Integer> nearestTuple, int width, int height) {
        List<Tuple<Integer, Integer>> result = new ArrayList<>();
        int currentX = nearestTuple._0;
        int currentY = nearestTuple._1;
        if (currentX > 0) {
            result.add(new Tuple<>(currentX - 1, currentY));
        }
        if (currentX < width - 1) {
            result.add(new Tuple<>(currentX + 1, currentY));
        }
        if (currentY > 0) {
            result.add(new Tuple<>(currentX, currentY - 1));
        }
        if (currentY < height - 1) {
            result.add(new Tuple<>(currentX, currentY + 1));
        }
        return result;
    }

    private Integer wrap(int i) {
        if (i > 9) {
            return i % 9;
        }
        return i;
    }
}

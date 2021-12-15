package main.java.Day15;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;
import main.java.Day5.Tuple;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Day extends AbstractDay {

    int height;
    int width;
    int[][] nodes;

    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {

        Long timeStart = System.currentTimeMillis();
        List<String> lines = InputUtils.readInStringLines(getInputStream());
        height = lines.size();
        width = lines.get(0).length();
        nodes = buildNodes(lines);
        solveFirstQuestion();
        solveSecondQuestion();
        Long timeEnd = System.currentTimeMillis();
        System.out.println("Full Programm Time: " + (timeEnd - timeStart) + " ms.");
    }

    private int[][] buildNodes(List<String> lines) {
        int[][] nodes = new int[width][height];
        for (int y = 0; y < height; y++) {
            nodes[y] = Arrays.stream(lines.get(y).split("")).mapToInt(Integer::parseInt).toArray();
        }
        return nodes;
    }

    private void solveFirstQuestion() {
        int shortestDistance = findShortestBetween(new Tuple<>(0, 0), new Tuple<>(width - 1, height - 1));
        System.out.println("Part 1:");
        System.out.println(shortestDistance);
    }

    private void solveSecondQuestion() {
        nodes = enlargeNodes(nodes);
        width *= 5;
        height *= 5;
        int shortestDistance = findShortestBetween(new Tuple<>(0, 0), new Tuple<>(width - 1, height - 1));
        System.out.println("Part 2:");
        System.out.println(shortestDistance);
    }

    private int[][] enlargeNodes(int[][] nodes) {
        int[][] newNodes = new int[5 * width][5 * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        newNodes[i * width + x][j * height + y] = wrap(nodes[x][y] + i + j);
                    }
                }
            }
        }
        return newNodes;
    }

    private int findShortestBetween(Tuple<Integer, Integer> start, Tuple<Integer, Integer> end) {


        Long timeStart = System.currentTimeMillis();
        int[][] distances = new int[width][height];
        ComparatorByValue comparator = new ComparatorByValue(distances);
        PriorityQueue<Tuple<Integer, Integer>> unvisited = new PriorityQueue<>(width * height, comparator);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                distances[x][y] = Integer.MAX_VALUE;
                unvisited.add(new Tuple<>(x, y));
            }
        }

        distances[start._0][start._1] = 0;

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

    private void updateDistanceViaNeightbours(Tuple<Integer, Integer> nearestTuple, PriorityQueue<Tuple<Integer, Integer>> unvisited, int[][] distances) {
        int nearestDist = distances[nearestTuple._0][nearestTuple._1];
        int nearX = nearestTuple._0;
        int nearY = nearestTuple._1;
        Tuple<int[], int[]> adjacents = getAdjacents(nearestTuple, width, height);
        for (int x : adjacents._0) {
            int temp = nearestDist + nodes[x][nearY];
            if (temp < distances[x][nearY]) {
                distances[x][nearY] = temp;
                // We can just add the same vertex again with another (higher) priority
                unvisited.add(new Tuple<>(x, nearY));
            }
        }
        for (int y : adjacents._1) {
            int temp = nearestDist + nodes[nearX][y];
            if (temp < distances[nearX][y]) {
                distances[nearX][y] = temp;
                // We can just add the same vertex again with another (higher) priority
                unvisited.add(new Tuple<>(nearX, y));
            }
        }
    }

    private Tuple<int[], int[]> getAdjacents(Tuple<Integer, Integer> nearestTuple, int width, int height) {
        Tuple<int[], int[]> result = new Tuple<>();
        int currentX = nearestTuple._0;
        int currentY = nearestTuple._1;

        if (currentX == 0) {
            result._0 = new int[]{1};
        } else if (currentX == width - 1) {
            result._0 = new int[]{width - 2};
        } else {
            result._0 = new int[]{currentX - 1, currentX + 1};
        }

        if (currentY == 0) {
            result._1 = new int[]{1};
        } else if (currentY == height - 1) {
            result._1 = new int[]{height - 2};
        } else {
            result._1 = new int[]{currentY - 1, currentY + 1};
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

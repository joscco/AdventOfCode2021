package main.java.Day15;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;
import main.java.Day5.Tuple;

import java.util.*;

public class Day extends AbstractDay {

    int height;
    int width;
    Map<Tuple<Integer, Integer>, Integer> nodes;

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

    private Map<Tuple<Integer, Integer>, Integer> buildNodes(List<String> lines) {
        Map<Tuple<Integer, Integer>, Integer> nodes = new HashMap<>();
        for (int y = 0; y < height; y++) {
            int[] values = Arrays.stream(lines.get(y).split("")).mapToInt(Integer::parseInt).toArray();
            for (int x = 0; x < width; x++) {
                nodes.put(new Tuple<>(x, y), values[x]);
            }
        }
        return nodes;
    }

    private void solveFirstQuestion() {
        long shortestDistance = findShortestBetween(nodes, new Tuple<>(0, 0), new Tuple<>(width - 1, height - 1));
        System.out.println("Part 1:");
        System.out.println(shortestDistance);
    }

    private void solveSecondQuestion() {
        nodes = enlargeNodes(nodes);
        width *= 5;
        height *= 5;
        long shortestDistance = findShortestBetween(nodes, new Tuple<>(0, 0), new Tuple<>(width - 1, height - 1));
        System.out.println("Part 2:");
        System.out.println(shortestDistance);
    }

    private Map<Tuple<Integer, Integer>, Integer> enlargeNodes(Map<Tuple<Integer, Integer>, Integer> nodes) {
        Map<Tuple<Integer, Integer>, Integer> newNodes = new HashMap<>();
        for (Tuple<Integer, Integer> node : nodes.keySet()) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    newNodes.put(new Tuple<>(i * width + node._0, j * height + node._1), wrap(nodes.get(node) + i + j));
                }
            }

        }
        return newNodes;
    }

    private long findShortestBetween(Map<Tuple<Integer, Integer>, Integer> nodes, Tuple<Integer, Integer> start, Tuple<Integer, Integer> end) {

        Map<Tuple<Integer, Integer>, Long> distances = new HashMap<>();
        ComparatorByValue comparator = new ComparatorByValue(distances);
        PriorityQueue<Tuple<Integer, Integer>> unvisited = new PriorityQueue<>(nodes.size(), comparator);
        for (Tuple<Integer, Integer> node : nodes.keySet()) {
            distances.put(node, (long) Integer.MAX_VALUE);
            unvisited.add(node);
        }

        distances.replace(start, 0L);
        Long timeStart = System.currentTimeMillis();

        while (true) {
            Tuple<Integer, Integer> nearestTuple = unvisited.poll();
            if (end.equals(nearestTuple)) {
                Long timeEnd = System.currentTimeMillis();
                System.out.println("Time passed: " + (timeEnd - timeStart) / 1000 + "s");
                return distances.get(nearestTuple);
            }

            updateDistanceViaNeightbours(nearestTuple, unvisited, distances);
        }
    }

    private void updateDistanceViaNeightbours(Tuple<Integer, Integer> nearestTuple, PriorityQueue<Tuple<Integer, Integer>> unvisited, Map<Tuple<Integer, Integer>, Long> distances) {
        Long nearestDist = distances.get(nearestTuple);
        for (Tuple<Integer, Integer> currentVertex : getAdjacents(nearestTuple, width, height)) {
            long temp = nearestDist + nodes.get(currentVertex);
            if (temp < distances.get(currentVertex)) {
                distances.put(currentVertex, temp);
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

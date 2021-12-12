package main.java.Day12;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day extends AbstractDay {

    Map<String, List<String>> paths;

    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {
        List<String> lines = InputUtils.readInStringLines(getInputStream());
        paths = buildPaths(lines);
        solveFirstQuestion();
        solveSecondQuestion();
    }

    private Map<String, List<String>> buildPaths(List<String> lines) {
        Map<String, List<String>> pathMap = new HashMap<>();
        for (String line : lines) {
            String[] nodes = line.split("-");
            addNodeCombination(pathMap, nodes[0], nodes[1]);
            addNodeCombination(pathMap, nodes[1], nodes[0]);
        }
        return pathMap;
    }

    private void addNodeCombination(Map<String, List<String>> pathMap, String from, String to) {
        // Start and End Node are uni-directional
        if ("start".equals(to) || "end".equals(from)) {
            return;
        }

        if (pathMap.containsKey(from)) {
            pathMap.get(from).add(to);
        } else {
            List<String> nodeList = new ArrayList<>();
            nodeList.add(to);
            pathMap.put(from, nodeList);
        }
    }

    private void solveFirstQuestion() {
        String currentNode = "start";
        List<String> visited = List.of(currentNode);

        System.out.println("Part 1:");
        System.out.println(findNumberOfPathsFromHere(currentNode, visited));
    }

    private void solveSecondQuestion() {
        String currentNode = "start";
        List<String> visited = List.of(currentNode);

        System.out.println("Part 2:");
        System.out.println(findNumberOfPathsFromHere(currentNode, visited, false));
    }

    private int findNumberOfPathsFromHere(String currentNode, List<String> visited) {
        int counterForNextWays = 0;
        for (String nextNode : paths.get(currentNode)) {
            if ("end".equals(nextNode)) {
                counterForNextWays++;
            } else {
                if (!visited.contains(nextNode) || isBigCave(nextNode)) {
                    List<String> newVisited = new ArrayList<>(visited);
                    if (!newVisited.contains(nextNode)) {
                        newVisited.add(nextNode);
                    }
                    counterForNextWays += findNumberOfPathsFromHere(nextNode, newVisited);
                }
            }
        }
        return counterForNextWays;
    }

    private int findNumberOfPathsFromHere(String currentNode, List<String> visited, boolean specialSmallVisited) {
        int counterForNextWays = 0;
        for (String nextNode : paths.get(currentNode)) {
            if ("end".equals(nextNode)) {
                counterForNextWays++;
            } else {
                boolean isSpecialSmallCase = visited.contains(nextNode) && !isBigCave(nextNode) && !specialSmallVisited;
                if (!visited.contains(nextNode) || isBigCave(nextNode) || isSpecialSmallCase) {
                    boolean newSpecialSmallVisited = specialSmallVisited || isSpecialSmallCase;
                    List<String> newVisited = new ArrayList<>(visited);
                    if (!newVisited.contains(nextNode)) {
                        newVisited.add(nextNode);
                    }
                    counterForNextWays += findNumberOfPathsFromHere(nextNode, newVisited, newSpecialSmallVisited);
                }
            }
        }
        return counterForNextWays;
    }

    private boolean isBigCave(String nextNode) {
        return nextNode.equals(nextNode.toUpperCase());
    }
}

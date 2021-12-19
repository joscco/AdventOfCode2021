package main.java.Day18;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;

import java.util.List;

public class Day extends AbstractDay {

    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {
        List<String> lines = InputUtils.readInStringLines(getInputStream());
        solveFirstQuestion(lines);
        solveSecondQuestion(lines);
    }

    private void solveFirstQuestion(List<String> lines) {
        SNode currentNode = createNode(lines.get(0), 1);
        for (int i = 1; i < lines.size(); i++) {
            SNode next = createNode(lines.get(i), 1);
            currentNode = currentNode.add(next);
            currentNode = currentNode.reduce();
        }
        System.out.println("Part 1:");
        System.out.println("Magnitude Sum: " + currentNode.getMagnitude());
    }

    private SNode createNode(String str, int depth) {
        str = stripOuterBrackets(str);
        int beginFirstSubNode;
        int endFirstSubNode;
        int beginSecondSubNode;
        int endSecondSubNode;
        SNode left;
        SNode right;
        if (str.charAt(0) == '[') {
            beginFirstSubNode = 0;
            endFirstSubNode = findFirstEndPosition(str);
            left = createNode(str.substring(beginFirstSubNode, endFirstSubNode + 1), depth + 1);
        } else {
            beginFirstSubNode = endFirstSubNode = 0;
            left = new SNode(Integer.parseInt(str.substring(beginFirstSubNode, endFirstSubNode + 1)), depth + 1);
        }

        if (str.charAt(endFirstSubNode + 2) == '[') {
            beginSecondSubNode = 2 + endFirstSubNode;
            endSecondSubNode = beginSecondSubNode + findFirstEndPosition(str.substring(beginSecondSubNode));
            right = createNode(str.substring(beginSecondSubNode, endSecondSubNode + 1), depth + 1);
        } else {
            beginSecondSubNode = endSecondSubNode = endFirstSubNode + 2;
            right = new SNode(Integer.parseInt(str.substring(beginSecondSubNode, endSecondSubNode + 1)), depth + 1);
        }
        return new SNode(left, right, depth);
    }

    private int findFirstEndPosition(String substring) {
        int bracketCounter = 1;
        for (int i = 1; i < substring.length(); i++) {
            if (substring.charAt(i) == '[') {
                bracketCounter++;
            } else if (substring.charAt(i) == ']') {
                bracketCounter--;
            }
            if (bracketCounter == 0) {
                return i;
            }
        }
        return -1;
    }

    private String stripOuterBrackets(String str) {
        return str.substring(1, str.length() - 1);
    }

    private void solveSecondQuestion(List<String> lines) {
        int maxSumSoFar = 0;
        for(int i = 0; i<lines.size(); i++) {
            for(int j = i+1; j<lines.size(); j++) {
                SNode first = createNode(lines.get(i),1);
                SNode second = createNode(lines.get(j), 1);
                int firstSumMagnitude = first.add(second).reduce().getMagnitude();

                SNode firstB = createNode(lines.get(i),1);
                SNode secondB = createNode(lines.get(j), 1);
                int secondSumMagnitude = secondB.add(firstB).reduce().getMagnitude();

                if(maxSumSoFar < firstSumMagnitude) {
                    maxSumSoFar = firstSumMagnitude;
                }
                if(maxSumSoFar < secondSumMagnitude) {
                    maxSumSoFar = secondSumMagnitude;
                }
            }
        }
        System.out.println("Part 2:");
        System.out.println("Max Magnitude Sum: " + maxSumSoFar);
    }
}

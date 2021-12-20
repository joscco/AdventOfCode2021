package main.java.Day19;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;
import main.java.Day5.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day extends AbstractDay {

    List<BeaconDetection> beaconDetections;

    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {
        List<String> lines = InputUtils.readInLinesWithEmpties(getInputStream());
        beaconDetections = buildBeaconDetections(lines);
        solveFirstAndSecondQuestion();
    }

    private List<BeaconDetection> buildBeaconDetections(List<String> lines) {
        List<BeaconDetection> result = new ArrayList<>();
        boolean readingPositions = false;
        BeaconDetection currentDetection = null;

        int currentIndex = 0;

        for (String line : lines) {
            if (readingPositions) {
                if (line == null || line.isEmpty()) {
                    readingPositions = false;
                    currentDetection.buildDistances();
                    result.add(currentDetection);
                    currentIndex++;
                } else {
                    int[] position = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
                    currentDetection.positions.add(position);
                }
            } else if (line.charAt(0) == '-') {
                readingPositions = true;
                currentDetection = new BeaconDetection();
                currentDetection.index = currentIndex;
            }
        }

        return result;
    }

    private void solveFirstAndSecondQuestion() {
        List<int[]> positions = new ArrayList<>();
        positions.add(new int[]{0, 0, 0});

        BeaconDetection master = beaconDetections.get(0).copy();

        List<BeaconDetection> included = new ArrayList<>();
        included.add(beaconDetections.get(0));
        List<BeaconDetection> toInclude = new ArrayList<>(beaconDetections);
        toInclude.remove(beaconDetections.get(0));

        while (!toInclude.isEmpty()) {
            outer:
            for (int i = 0; i < toInclude.size(); i++) {
                for (int j = 0; j < included.size(); j++) {
                    BeaconDetection candidate = toInclude.get(i);
                    BeaconDetection oldBoy = included.get(j);
                    if (candidate.mightAlignWith(oldBoy)) {
                        Tuple<BeaconDetection, int[]> aligned = candidate.alignWith(oldBoy);
                        if (aligned._0 != null) {
                            master = master.integrate(oldBoy.integrate(aligned._0));
                            candidate.buildDistances();
                            aligned._0.buildDistances();
                            included.add(aligned._0);
                            positions.add(aligned._1);
                            toInclude.remove(candidate);
                            break outer;
                        }
                    }
                }
            }
        }

        System.out.println("Part 1:");
        System.out.println(master.positions.size());
        System.out.println("Part 2:");
        System.out.println(getMaxManhattanDist(positions));
    }

    private int getMaxManhattanDist(List<int[]> positions) {
        int maxDist = 0;
        for (int[] pos : positions) {
            for (int[] posB : positions) {
                int manhattanDist = calculateManhattan(pos, posB);
                if (manhattanDist > maxDist) {
                    maxDist = manhattanDist;
                }
            }
        }
        return maxDist;
    }

    private int calculateManhattan(int[] pos, int[] posB) {
        return Math.abs(pos[0] - posB[0]) + Math.abs(pos[1] - posB[1]) + Math.abs(pos[2] - posB[2]);
    }
}

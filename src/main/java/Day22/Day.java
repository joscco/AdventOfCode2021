package main.java.Day22;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day extends AbstractDay {

    public static void main(String[] args) {
        Day day = new Day();
        long startTime = System.currentTimeMillis();
        day.solveDay();
        long endTime = System.currentTimeMillis();
        System.out.println("Time needed: " + (endTime - startTime) + "ms");
    }

    public void solveDay() {
        List<String> lines = InputUtils.readInStringLines(getInputStream());
        List<Cuboid> cuboids = buildCuboids(lines);
        List<Cuboid> mergedCuboids = mergeCuboids(cuboids);
        solveFirstQuestion(mergedCuboids);
        solveSecondQuestion(mergedCuboids);
    }

    private List<Cuboid> mergeCuboids(List<Cuboid> cuboids) {
        List<Cuboid> alreadyPlacedCuboids = new ArrayList<>();
        for (Cuboid newCuboid : cuboids) {
            List<Cuboid> balancingCuboids = new ArrayList<>();

            if (newCuboid.on()) {
                // Add one weight for each new on cuboid point
                balancingCuboids.add(newCuboid);
            }

            // If off-cuboids do not have any intersections with old ones, we won't deal with them.
            // We will work with weights. On Cuboids have one weight for each point.
            // Instead of slicing up all cuboids, only add negative weight intersection when turning off points again.
            for (Cuboid oldCuboid : alreadyPlacedCuboids) {

                // This gives four cases for intersection:
                // Old on, new on (-> weight=2 for each intersection point):
                // Add intersection with negative weight to return to weight 0 there
                // Old on, new off (-> weight=1 for each intersection point):
                // Add intersection with negative weight to return to weight 0 there
                // Old off (already cancelling out something), new on (-> weight=0 for each intersection point):
                // Add intersection with positive weight to return to weight 1 there
                // Old off (already cancelling out something), new off (-> weight=-1 for each intersection point):
                // Add intersection with positive weight to return to weight 1 there

                // So only the old value really counts

                Cuboid intersection = oldCuboid.buildIntersectionWith(newCuboid);
                if (intersection != null) {
                    balancingCuboids.add(intersection);
                }
            }

            alreadyPlacedCuboids.addAll(balancingCuboids);
        }
        return alreadyPlacedCuboids;
    }

    private List<Cuboid> buildCuboids(List<String> lines) {
        List<Cuboid> result = new ArrayList<>();
        for (String line : lines) {
            String[] lineArr = line.split(" ");

            String[] limitArr = lineArr[1].split(",");
            int[] values = Arrays.stream(limitArr)
                    .map(str -> str.substring(2))
                    .map(str -> str.split("\\.\\."))
                    .flatMap(Stream::of)
                    .mapToInt(Integer::parseInt)
                    .toArray();

            Cuboid cuboid = new Cuboid(
                    values[0], values[1],
                    values[2], values[3],
                    values[4], values[5],
                    lineArr[0].equals("on"));
            result.add(cuboid);

        }
        return result;
    }

    private void solveFirstQuestion(List<Cuboid> cuboids) {
        System.out.println("Part 1:");
        System.out.println(cuboids.stream().mapToLong(c -> c.limitedAxisVolume(-50, 50)).sum());
    }

    private void solveSecondQuestion(List<Cuboid> cuboids) {
        System.out.println("Part 2:");
        System.out.println(cuboids.stream().mapToLong(Cuboid::volume).sum());
    }
}

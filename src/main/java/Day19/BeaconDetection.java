package main.java.Day19;

import main.java.Day5.Tuple;

import java.util.*;

public class BeaconDetection {

    Set<int[]> positions;
    List<Integer> distances;
    int index;

    public BeaconDetection() {
        positions = new HashSet<>();
        distances = new ArrayList<>();
    }

    public BeaconDetection copy() {
        BeaconDetection copy = new BeaconDetection();
        copy.distances = new ArrayList<>(distances);
        copy.positions = new HashSet<>(positions);
        copy.index = index;
        return copy;
    }

    public Tuple<BeaconDetection, int[]> alignWith(BeaconDetection other) {
        for (List<int[]> turnedPositions : getTurnedPositions(this.positions)) {
            for (int[] pos : turnedPositions) {
                for (int[] otherPos : other.positions) {
                    int intersections = countDifferenceIntersection(pos, turnedPositions, otherPos, other.positions);
                    if (intersections >= 11) {
                        int offSetX = otherPos[0] - pos[0];
                        int offSetY = otherPos[1] - pos[1];
                        int offSetZ = otherPos[2] - pos[2];
                        Set<int[]> translatedPositions = translatePositions(turnedPositions, offSetX, offSetY, offSetZ);
                        if (other.countPositionIntersections(translatedPositions) >= 12) {
                            BeaconDetection result = new BeaconDetection();
                            result.index = index;
                            result.positions = translatedPositions;
                            return new Tuple<>(result, new int[]{offSetX, offSetY, offSetZ});
                        }
                    }
                }
            }
        }
        return null;
    }

    // This whole "turning" thing could sure be optimised

    private List<List<int[]>> getTurnedPositions(Collection<int[]> positions) {
        List<List<int[]>> result = new ArrayList<>();

        List<int[]> normal = new ArrayList<>(positions);
        result.add(normal);
        result.add(turnX(normal, 1));
        result.add(turnX(normal, 2));
        result.add(turnX(normal, 3));

        List<int[]> reverseX = turnZ(normal, 2);
        result.add(reverseX);
        result.add(turnX(reverseX, 1));
        result.add(turnX(reverseX, 2));
        result.add(turnX(reverseX, 3));

        List<int[]> plusY = turnZ(normal, 1);
        result.add(plusY);
        result.add(turnY(plusY, 1));
        result.add(turnY(plusY, 2));
        result.add(turnY(plusY, 3));

        List<int[]> reverseY = turnZ(normal, 3);
        result.add(reverseY);
        result.add(turnY(reverseY, 1));
        result.add(turnY(reverseY, 2));
        result.add(turnY(reverseY, 3));

        List<int[]> plusZ = turnY(normal, 1);
        result.add(plusZ);
        result.add(turnZ(plusZ, 1));
        result.add(turnZ(plusZ, 2));
        result.add(turnZ(plusZ, 3));

        List<int[]> reverseZ = turnY(normal, 3);
        result.add(reverseZ);
        result.add(turnZ(reverseZ, 1));
        result.add(turnZ(reverseZ, 2));
        result.add(turnZ(reverseZ, 3));

        return result;

    }

    private List<int[]> turnX(List<int[]> old, int howOften) {
        List<int[]> result = new ArrayList<>();
        for (int[] pos : old) {
            result.add(turnX(pos, howOften));
        }
        return result;
    }

    private List<int[]> turnY(List<int[]> old, int howOften) {
        List<int[]> result = new ArrayList<>();
        for (int[] pos : old) {
            result.add(turnY(pos, howOften));
        }
        return result;
    }

    private List<int[]> turnZ(List<int[]> old, int howOften) {
        List<int[]> result = new ArrayList<>();
        for (int[] pos : old) {
            result.add(turnZ(pos, howOften));
        }
        return result;
    }

    private int[] turnX(int[] old, int howOften) {
        int[] result = old;
        for (int i = 0; i < howOften; i++) {
            result = turnX(result);
        }
        return result;
    }

    private int[] turnY(int[] old, int howOften) {
        int[] result = old;
        for (int i = 0; i < howOften; i++) {
            result = turnY(result);
        }
        return result;
    }

    private int[] turnZ(int[] old, int howOften) {
        int[] result = old;
        for (int i = 0; i < howOften; i++) {
            result = turnZ(result);
        }
        return result;
    }

    private int[] turnX(int[] old) {
        return new int[]{old[0], old[2], -old[1]};
    }

    private int[] turnY(int[] old) {
        return new int[]{-old[2], old[1], old[0]};
    }

    private int[] turnZ(int[] old) {
        return new int[]{old[1], -old[0], old[2]};
    }

    private int countPositionIntersections(Set<int[]> positions) {
        int counter = 0;
        for (int[] pos : positions) {
            if (containsPosition(pos)) {
                counter++;
            }
        }
        return counter;
    }

    private Set<int[]> translatePositions(Collection<int[]> turnedPositions, int offSetX, int offSetY, int offSetZ) {
        Set<int[]> result = new HashSet<>(turnedPositions);
        for (int[] pos : result) {
            pos[0] += offSetX;
            pos[1] += offSetY;
            pos[2] += offSetZ;
        }
        return result;
    }

    private int countDifferenceIntersection(int[] pos, Collection<int[]> turnedPositions, int[] masterPos, Collection<int[]> positions) {
        List<Integer> differencesA = buildPointDistances(pos, turnedPositions);
        List<Integer> differencesB = buildPointDistances(masterPos, positions);
        int counter = 0;
        for (int val : differencesA) {
            for (int val2 : differencesB) {
                if (val == val2) {
                    counter++;
                }
            }
        }
        return counter;
    }


    private List<Integer> buildPointDistances(int[] pos, Collection<int[]> turnedPositions) {
        List<Integer> result = new ArrayList<>();
        for (int[] other : turnedPositions) {
            if (squareDist(pos, other) != 0) {
                result.add(squareDist(pos, other));
            }
        }
        return result;
    }

    private int squareDist(int[] pos, int[] other) {
        int result = 0;
        for (int i = 0; i <= 2; i++) {
            result += (pos[i] - other[i]) * (pos[i] - other[i]);
        }
        return result;
    }

    public BeaconDetection integrate(BeaconDetection aligned) {
        BeaconDetection result = new BeaconDetection();
        result.positions = new HashSet<>(positions);

        for (int[] position : aligned.positions) {
            if (!result.containsPosition(position)) {
                result.positions.add(position);
            }
        }

        result.buildDistances();
        return result;
    }

    private boolean containsPosition(int[] position) {
        for (int[] myPos : positions) {
            if (position[0] == myPos[0] && position[1] == myPos[1] && position[2] == myPos[2]) {
                return true;
            }
        }
        return false;
    }

    public void buildDistances() {
        distances = new ArrayList<>();
        List<int[]> localPositions = new ArrayList<>(positions);
        for (int i = 0; i < positions.size(); i++) {
            for (int j = i + 1; j < positions.size(); j++) {
                int[] posA = localPositions.get(i);
                int[] posB = localPositions.get(j);
                distances.add(squareDist(posA, posB));
            }
        }
    }

    public boolean mightAlignWith(BeaconDetection current) {
        int counter = 0;
        for (int distance : distances) {
            if (current.distances.contains(distance)) {
                counter++;
            }
        }

        // Use (12 choose 2) here
        return counter >= (12 * 11) / 2;
    }
}

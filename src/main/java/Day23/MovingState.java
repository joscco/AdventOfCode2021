package main.java.Day23;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record MovingState(List<List<String>> slots, List<String> hallway, int cost) {

    private static final List<Integer> HALLWAY_POSITIONS = List.of(0, 1, 3, 5, 7, 9, 10);

    public MovingState moveOut(int slotNumber, String pod, int slotPosition, int hallwayPosition) {
        if (!hallwayIsFreeBetween(slotToHallwayPosition(slotNumber), hallwayPosition)) {
            return null;
        }

        List<List<String>> newSlots = copySlots(slots);
        List<String> newHallway = copySlot(hallway);

        newSlots.get(slotNumber).set(slotPosition, "");
        newHallway.set(hallwayPosition, pod);

        int newCost = cost + calcCostFromSlotToHallway(pod, slotNumber,
                slotPosition, hallwayPosition);
        return new MovingState(newSlots, newHallway, newCost);
    }

    public MovingState moveIn(String pod, int currentPos) {
        if (!hallwayIsFreeBetweenExcept(currentPos, slotToHallwayPosition(slotForPod(pod)), currentPos)) {
            return null;
        }

        int slotNumber = slotForPod(pod);
        int newSlotPosition = findFirstFreeInSlot(slotNumber);

        List<List<String>> newSlots = copySlots(slots);
        List<String> newHallway = copySlot(hallway);

        newSlots.get(slotNumber).set(newSlotPosition, pod);
        newHallway.set(currentPos, "");

        int newCost = cost + calcCostFromSlotToHallway(pod, slotNumber, newSlotPosition, currentPos);
        return new MovingState(newSlots, newHallway, newCost);
    }

    private int findFirstFreeInSlot(int slotNumber) {
        for (int i = 0; i < slots.get(slotNumber).size(); i++) {
            if ("".equals(slots.get(slotNumber).get(i))) {
                return i;
            }
        }
        throw new IllegalStateException("No Free Slot!");
    }

    private List<String> copySlot(List<String> slot) {
        return new ArrayList<>(slot);
    }

    private List<List<String>> copySlots(List<List<String>> slots) {
        List<List<String>> result = new ArrayList<>();
        for (List<String> slot : slots) {
            result.add(copySlot(slot));
        }
        return result;
    }

    private int calcCostFromSlotToHallway(String pod, int slotNumber, int slotPosition, int hallwayPosition) {
        int slotInHallWayPosition = slotToHallwayPosition(slotNumber);
        int stepsSide = Math.abs(hallwayPosition - slotInHallWayPosition);
        int steps = 1 + slotPosition + stepsSide;
        return steps * costForPod(pod);
    }

    public static int slotForPod(String pod) {
        return switch (pod) {
            case "A" -> 0;
            case "B" -> 1;
            case "C" -> 2;
            case "D" -> 3;
            default -> -1;
        };
    }

    public static int costForPod(String pod) {
        return switch (pod) {
            case "A" -> 1;
            case "B" -> 10;
            case "C" -> 100;
            case "D" -> 1000;
            default -> 0;
        };
    }

    private static int slotToHallwayPosition(int slotNumber) {
        return 2 + 2 * slotNumber;
    }

    public boolean hallwayIsFreeBetween(int a, int b) {
        return hallwayIsFreeBetweenExcept(a, b, -1);
    }

    public boolean hallwayIsFreeBetweenExcept(int a, int b, int c) {
        int max = Math.max(a, b);
        int min = Math.min(a, b);

        for (int i = min; i <= max; i++) {
            if (i != c && !isFreeHallwayPosition(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean isFreeHallwayPosition(int i) {
        return hallway.get(i).equals("");
    }

    public boolean slotIsClean(int slotNumber) {
        for (String pod : slots.get(slotNumber)) {
            if ((!"".equals(pod)) && slotNumber != slotForPod(pod)) {
                return false;
            }
        }
        return true;
    }

    public boolean isFinished() {
        for (int i = 0; i < 4; i++) {
            for (String pod : slots.get(i)) {
                if ("".equals(pod) || i != slotForPod(pod)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int calculateMinCosts() {
        Set<MovingState> currentStates = Set.of(this);
        int minCost = 100000;
        Set<MovingState> statesSoFar;

        for (int n = 0; !currentStates.isEmpty(); n++) {
            statesSoFar = new HashSet<>();
            Set<MovingState> stateCopies = new HashSet<>(currentStates);
            currentStates = new HashSet<>();
            System.out.println("------ NEW RUN " + n + " --------");
            System.out.println("Old States: " + stateCopies.size());

            for (MovingState currentState : stateCopies) {
                if (statesSoFar.contains(currentState)) {
                    continue;
                }

                statesSoFar.add(currentState);

                if (currentState.isFinished()) {
                    if (currentState.cost() < minCost) {
                        minCost = currentState.cost();
                    }
                    continue;
                }

                // Moving In
                for (int i = 0; i < 11; i++) {
                    String pod = currentState.hallway().get(i);
                    if ((!pod.equals("")) && currentState.slotIsClean(slotForPod(pod))) {
                        MovingState newState = currentState.moveIn(pod, i);
                        if (newState != null) {
                            currentStates.add(newState);
                        }
                    }
                }

                // Moving out
                for (int slotNumber = 0; slotNumber < 4; slotNumber++) {
                    List<String> slot = currentState.slots().get(slotNumber);
                    for (int pos = 0; pos < slot.size(); pos++) {
                        if (!"".equals(slot.get(pos))) {
                            String pod = slot.get(pos);
                            if (slotContainsWrongPodsBelowPosition(slot, pos, slotNumber)) {
                                for (int hallWayPos : HALLWAY_POSITIONS) {
                                    MovingState newState = currentState.moveOut(slotNumber, pod, pos, hallWayPos);
                                    if (newState != null) {
                                        currentStates.add(newState);
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        return minCost;
    }

    public static boolean slotContainsWrongPodsBelowPosition(List<String> slot, int pos, int slotNumber) {
        for (int i = pos; i < slot.size(); i++) {
            if (!"".equals(slot.get(i)) && slotForPod(slot.get(i)) != slotNumber) {
                return true;
            }
        }
        return false;
    }
}

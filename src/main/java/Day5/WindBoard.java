package main.java.Day5;

import java.util.HashMap;
import java.util.Map;

public class WindBoard {

    private final Map<Tuple<Integer, Integer>, Integer> windForFieldCounter;

    public WindBoard() {
        windForFieldCounter = new HashMap<>();
    }

    public void addPathToBoard(int[] begin, int[] end) {
        int diffX = end[0] - begin[0];
        int diffY = end[1] - begin[1];
        int stepX = (int) Math.signum(diffX);
        int stepY = (int) Math.signum(diffY);
        int pathLength = Math.max(Math.abs(diffX), Math.abs(diffY));
        Tuple<Integer, Integer> currentPos = new Tuple<>();
        for (int i = 0; i <= pathLength; i++) {
            currentPos._0 = begin[0] + i * stepX;
            currentPos._1 = begin[1] + i * stepY;
            if (!windForFieldCounter.containsKey(currentPos)) {
                windForFieldCounter.put(new Tuple<>(currentPos._0, currentPos._1), 1);
            } else {
                windForFieldCounter.replace(new Tuple<>(currentPos._0, currentPos._1), windForFieldCounter.get(currentPos) + 1);
            }
        }
    }

    public void countTurbulentFields() {
        int turbulentFieldsCounter = 0;
        for (Tuple<Integer, Integer> tuple : windForFieldCounter.keySet()) {
            // Count each field where at least two wind paths meet
            if (windForFieldCounter.get(tuple) > 1) {
                turbulentFieldsCounter++;
            }
        }
        System.out.println(turbulentFieldsCounter);
    }


}

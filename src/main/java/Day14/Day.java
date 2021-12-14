package main.java.Day14;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;
import main.java.Day5.Tuple;

import java.util.*;

public class Day extends AbstractDay {

    String template = "FSKBVOSKPCPPHVOPVFPC";
    Map<String, Tuple<String, String>> replacements;

    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {
        List<String> lines = InputUtils.readInStringLines(getInputStream());
        replacements = buildReplacements(lines);
        solveFirstQuestion();
        solveSecondQuestion();
    }

    private Map<String, Tuple<String, String>> buildReplacements(List<String> lines) {
        Map<String, Tuple<String, String>> map = new HashMap<>();
        for (String line : lines) {
            String[] ruleParts = line.split(" -> ");
            String firstNewPair = ruleParts[0].charAt(0) + ruleParts[1];
            String secondNewPair = ruleParts[1] + ruleParts[0].charAt(1);
            map.put(ruleParts[0], new Tuple<>(firstNewPair, secondNewPair));
        }
        return map;
    }

    private void solveFirstQuestion() {
        Map<String, Long> counterMap = initializePairMap(template);
        for (int i = 0; i < 10; i++) {
            counterMap = updateStep(counterMap);
        }

        Long maxValue = findMax(counterMap);
        Long minValue = findMin(counterMap);
        System.out.println("Part 1:");
        System.out.println(maxValue - minValue);
    }

    private void solveSecondQuestion() {
        Map<String, Long> counterMap = initializePairMap(template);
        for (int i = 0; i < 40; i++) {
            counterMap = updateStep(counterMap);
        }

        Long maxValue = findMax(counterMap);
        Long minValue = findMin(counterMap);
        System.out.println("Part 2:");
        System.out.println(maxValue - minValue);
    }

    private Map<String, Long> initializePairMap(String template) {
        Map<String, Long> result = new HashMap<>();
        for (int i = 0; i < template.length() - 1; i++) {
            String currentPair = template.substring(i, i + 2);
            addOrIncreaseCounter(result, currentPair, 1L);
        }
        return result;
    }

    private Long findMin(Map<String, Long> counterMap) {
        Map<String, Long> letterCounter = new HashMap<>();

        for (String pair : counterMap.keySet()) {
            addOrIncreaseCounter(letterCounter, pair.substring(0, 1), counterMap.get(pair));
        }

        // Add one for the last letter C in the Template which doesn't have a right pair partner
        addOrIncreaseCounter(letterCounter, "C", 1L);

        // Apparently C is a letter that will appear
        Long currentMin = letterCounter.get("C");
        for (String key : letterCounter.keySet()) {
            if (letterCounter.get(key) < currentMin) {
                currentMin = letterCounter.get(key);
            }
        }
        return currentMin;
    }

    private Long findMax(Map<String, Long> counterMap) {
        Map<String, Long> letterCounter = new HashMap<>();

        for (String pair : counterMap.keySet()) {
            addOrIncreaseCounter(letterCounter, pair.substring(0, 1), counterMap.get(pair));
        }

        Long currentMax = 0L;
        for (String key : letterCounter.keySet()) {
            if (letterCounter.get(key) > currentMax) {
                currentMax = letterCounter.get(key);
            }
        }
        return currentMax;
    }

    private Map<String, Long> updateStep(Map<String, Long> counterMap) {
        Set<String> keys = new HashSet<>(counterMap.keySet());
        Map<String, Long> newCounterMap = new HashMap<>();
        for (String key : keys) {
            Tuple<String, String> newPairs = replacements.get(key);
            Long numberOfAppearances = counterMap.get(key);
            addOrIncreaseCounter(newCounterMap, newPairs._0, numberOfAppearances);
            addOrIncreaseCounter(newCounterMap, newPairs._1, numberOfAppearances);
        }
        return newCounterMap;
    }

    private void addOrIncreaseCounter(Map<String, Long> counterMap, String newString, Long value) {
        if (counterMap.containsKey(newString)) {
            counterMap.replace(newString, counterMap.get(newString) + value);
        } else {
            counterMap.put(newString, value);
        }
    }
}

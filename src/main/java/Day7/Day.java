package main.java.Day7;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;

import java.util.List;

public class Day extends AbstractDay {

    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {
        List<Integer> values = InputUtils.readInCommaSeparatedInts(getInputStream());
        solveFirstQuestion(values);
        solveSecondQuestion(values);
    }

    private void solveFirstQuestion(List<Integer> values) {
        int bestValue = values.get(0);
        int bestFuelCos = Integer.MAX_VALUE;
        for(Integer v: values) {
            int currentCost = 0;
            for(Integer k: values) {
                currentCost += Math.abs(v - k);
            }
            if(bestFuelCos > currentCost) {
                bestValue = v;
                bestFuelCos = currentCost;
            }
        }
        System.out.println("Part 1");
        System.out.println("Best Value:" + bestValue);
        System.out.println("Best Fuel Cost:" + bestFuelCos);
    }

    private void solveSecondQuestion(List<Integer> values) {
        int bestValue = values.get(0);
        int bestFuelCos = Integer.MAX_VALUE;
        for(Integer v: values) {
            int currentCost = 0;
            for(Integer k: values) {
                int normalCost = Math.abs(v - k);
                currentCost += normalCost*(normalCost+1)/2;
            }
            if(bestFuelCos > currentCost) {
                bestValue = v;
                bestFuelCos = currentCost;
            }
        }
        System.out.println("Part 2");
        System.out.println("Best Value:" + bestValue);
        System.out.println("Best Fuel Cost:" + bestFuelCos);
    }

}

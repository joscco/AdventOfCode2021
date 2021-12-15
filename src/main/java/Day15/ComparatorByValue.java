package main.java.Day15;

import main.java.Day5.Tuple;

import java.util.Comparator;

public class ComparatorByValue implements Comparator<Tuple<Integer, Integer>> {
    private final int[][] arr;

    public ComparatorByValue(int[][] distances) {
        this.arr = distances;
    }


    @Override
    public int compare(Tuple<Integer, Integer> o1, Tuple<Integer, Integer> o2) {
        int val1 = arr[o1._0][o1._1];
        int val2 = arr[o2._0][o2._1];
        if (val1 != val2) {
            return val1 - val2;
        }
        return o1._0 + o1._1 - o2._0 - o2._1;
    }
}

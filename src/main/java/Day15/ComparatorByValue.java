package main.java.Day15;

import main.java.Day5.Tuple;

import java.util.Comparator;
import java.util.Map;

public class ComparatorByValue implements Comparator<Tuple<Integer, Integer>> {
    private final Map<Tuple<Integer, Integer>, Long> map;

    public ComparatorByValue(Map<Tuple<Integer, Integer>, Long> distances) {
        this.map = distances;
    }


    @Override
    public int compare(Tuple<Integer, Integer> o1, Tuple<Integer, Integer> o2) {
        long val1 = map.get(o1);
        long val2 = map.get(o2);
        if (val1 != val2) {
            return (int) (val1 - val2);
        }
        return o1._0 + o1._1 - o2._0 - o2._1;
    }
}

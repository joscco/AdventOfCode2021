package main.java.Day3;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;

import java.util.List;
import java.util.stream.Collectors;

public class Day extends AbstractDay {

    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {
        List<String> values = InputUtils.readInStringLines(getInputStream());
        solveFirstQuestion(values);
        solveSecondQuestion(values);
    }

    private static void solveFirstQuestion(List<String> values) {
        StringBuilder eps = new StringBuilder();
        StringBuilder gamma = new StringBuilder();

        for (int i = 0; i < values.get(0).length(); i++) {
            int oneCounter = 0;
            int zeroCounter = 0;

            for (String value : values) {
                if (value.charAt(i) == '0') {
                    zeroCounter++;
                } else {
                    oneCounter++;
                }
            }

            boolean zeroWins = zeroCounter > oneCounter;
            gamma.append(zeroWins ? "0" : "1");
            eps.append(zeroWins ? "1" : "0");
        }

        int gamma_dec = Integer.parseInt(gamma.toString(), 2);
        int eps_dec = Integer.parseInt(eps.toString(), 2);

        System.out.println("Gamma: " + gamma + ", In Decimal: " + gamma_dec);
        System.out.println("Eps: " + eps + ", In Decimal: " + eps_dec);
        System.out.println("Product: " + eps_dec * gamma_dec);
    }

    private static void solveSecondQuestion(List<String> values) {
        String oxy;
        String co2;
        List<String> valuesOxy = values;
        List<String> valuesCO2 = values;

        int prevIndex = 0;

        while (true) {
            // Stream demands final parameters
            final int currentIndex = prevIndex;
            int oneCounterOxy = 0;
            int zeroCounterOxy = 0;

            for (String value : valuesOxy) {
                if (value.charAt(currentIndex) == '0') {
                    zeroCounterOxy++;
                } else {
                    oneCounterOxy++;
                }
            }

            if (currentIndex < values.get(0).length()) {
                if (zeroCounterOxy > oneCounterOxy) {
                    valuesOxy = valuesOxy.stream()
                            .filter(str -> str.charAt(currentIndex) == '0')
                            .collect(Collectors.toList());
                } else {
                    valuesOxy = valuesOxy.stream()
                            .filter(str -> str.charAt(currentIndex) == '1')
                            .collect(Collectors.toList());
                }
            }

            if (valuesOxy.size() == 1) {
                oxy = valuesOxy.get(0);
                break;
            }

            prevIndex = currentIndex + 1;
        }

        prevIndex = 0;

        while (true) {
            final int currentIndex = prevIndex;
            int oneCounterCo2 = 0;
            int zeroCounterCo2 = 0;

            for (String value : valuesCO2) {
                if (value.charAt(currentIndex) == '0') {
                    zeroCounterCo2++;
                } else {
                    oneCounterCo2++;
                }
            }

            if (currentIndex < values.get(0).length()) {
                if (zeroCounterCo2 > oneCounterCo2) {
                    valuesCO2 = valuesCO2.stream()
                            .filter(str -> str.charAt(currentIndex) == '1')
                            .collect(Collectors.toList());
                } else {
                    valuesCO2 = valuesCO2.stream()
                            .filter(str -> str.charAt(currentIndex) == '0')
                            .collect(Collectors.toList());
                }
            }

            if (valuesCO2.size() == 1) {
                co2 = valuesCO2.get(0);
                break;
            }

            prevIndex = currentIndex + 1;
        }

        int oxy_dec = Integer.parseInt(oxy, 2);
        int co2_dec = Integer.parseInt(co2, 2);
        System.out.println("Oxy: " + co2 + ", In Decimal: " + oxy_dec);
        System.out.println("Co2: " + oxy + ", In Decimal: " + co2_dec);
        System.out.println("Product: " + oxy_dec * co2_dec);
    }

}

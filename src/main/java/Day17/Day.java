package main.java.Day17;

import main.java.Common.AbstractDay;
import main.java.Day5.Tuple;

public class Day extends AbstractDay {

    int minXField = 235;
    int maxXField = 259;
    int minYField = -118;
    int maxYField = -62;
    int startX = 0;
    int startY = 0;

    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {
        solveFirstAndSecondQuestion();
    }

    private void solveFirstAndSecondQuestion() {
        int highestHeight = 0;
        int dirCounter = 0;
        for (int x = 0; x < 50000; x++) {
            for (int y = -200; y < 5000; y++) {
                Tuple<Boolean, Integer> tuple = simulate(x, y);
                if (tuple._0) {
                    dirCounter++;
                    if (tuple._1 > highestHeight) {
                        highestHeight = tuple._1;
                    }
                }
            }
        }
        System.out.println("Part 1:");
        System.out.println(highestHeight);
        System.out.println("Part 2:");
        System.out.println(dirCounter);
    }

    private Tuple<Boolean, Integer> simulate(int x, int y) {
        boolean fieldIsHit = false;
        int currentX = startX;
        int currentY = startY;
        int currentBiggestHeight = startY;
        int currentVelX = x;
        int currentVelY = y;

        while (fieldCouldStillBeHitInX(currentX, currentVelX) && fieldCouldStillBeHitInY(currentY)) {
            currentX += currentVelX;
            currentY += currentVelY;
            if (currentY > currentBiggestHeight) {
                currentBiggestHeight = currentY;
            }
            if (pointIsInField(currentX, currentY)) {
                fieldIsHit = true;
                break;
            }
            currentVelX -= (int) Math.signum(currentVelX);
            currentVelY -= 1;
        }
        return new Tuple<>(fieldIsHit, currentBiggestHeight);
    }

    private boolean fieldCouldStillBeHitInY(int currentY) {
        return currentY >= minYField;
    }

    private boolean pointIsInField(int currentX, int currentY) {
        return (currentX <= maxXField) && (currentX >= minXField) && currentY <= maxYField && currentY >= minYField;
    }

    private boolean fieldCouldStillBeHitInX(int currentX, int currentVelX) {
        return currentX <= maxXField && !(currentVelX == 0 && currentX< minXField);
    }

}

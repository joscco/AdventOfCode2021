package main.java.Day20;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;

import java.util.Arrays;
import java.util.List;

public class Day extends AbstractDay {

    int[] translator;

    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {
        List<String> lines = InputUtils.readInLinesWithEmpties(getInputStream());
        String codeLine = lines.get(0);
        List<String> imageLines = lines.subList(2, lines.size() - 1);

        translator = buildTranslator(codeLine);

        System.out.println("Part 1:");
        System.out.println(countOnesAfterResharpeningImage(imageLines, 2));

        System.out.println("Part 2:");
        System.out.println(countOnesAfterResharpeningImage(imageLines, 50));
    }

    private int countOnesAfterResharpeningImage(List<String> imageLines, int steps) {
        int[][] firstImage = buildImage(imageLines);
        firstImage = sharpenImage(firstImage, steps);
        return countOnesWithOffset(firstImage, steps);
    }

    private int[][] extendNLines(int[][] image, int n) {
        int height = image.length;
        int width = image[0].length;
        int[][] result = new int[height + 2 * n][];

        for (int i = 0; i < n; i++) {
            result[i] = result[height + 2 * n - 1 - i] = new int[width + 2 * n];
        }

        for (int i = n; i <= height + n - 1; i++) {
            int[] newRow = new int[width + 2 * n];
            System.arraycopy(image[i - n], 0, newRow, n, width);
            result[i] = newRow;
        }
        return result;
    }

    private int[][] sharpenImage(int[][] image, int steps) {
        image = extendNLines(image, 2 * steps);
        int imageHeight = image.length;
        int imageWidth = image[0].length;
        for (int i = 0; i < steps; i++) {
            image = sharpenImage(image, imageHeight, imageWidth, translator);
        }
        return image;
    }

    private int countOnesWithOffset(int[][] image, int offSet) {
        int imageHeight = image.length;
        int imageWidth = image[0].length;
        int counter = 0;
        for (int i = offSet; i < imageHeight - offSet; i++) {
            for (int j = offSet; j < imageWidth - offSet; j++) {
                if (image[i][j] == 1) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private int[][] sharpenImage(int[][] image, int imageHeight, int imageWidth, int[] translator) {
        int[][] newInnerImage = new int[imageHeight][imageWidth];
        for (int x = 1; x < imageWidth - 1; x++) {
            for (int y = 1; y < imageHeight - 1; y++) {
                int codeValue = calculateWindowValue(image, x, y);
                newInnerImage[y][x] = translator[codeValue];
            }
        }
        return newInnerImage;
    }

    private int calculateWindowValue(int[][] image, int x, int y) {
        StringBuilder binary = new StringBuilder();
        for (int j = y - 1; j <= y + 1; j++) {
            for (int i = x - 1; i <= x + 1; i++) {
                binary.append(image[j][i]);
            }
        }

        return Integer.parseInt(binary.toString(), 2);
    }


    private int[][] buildImage(List<String> imageLines) {
        int[][] result = new int[imageLines.size()][];
        for (int i = 0; i < imageLines.size(); i++) {
            result[i] = parseStringLineToInt(imageLines.get(i).split(""));
        }
        return result;
    }

    private int[] parseStringLineToInt(String[] arr) {
        return Arrays.stream(arr).mapToInt(this::parseSign).toArray();
    }

    private int parseSign(String s) {
        return s.equals("#") ? 1 : 0;
    }

    private int[] buildTranslator(String codeLine) {
        int[] result = new int[512];
        for (int i = 0; i < 512; i++) {
            result[i] = codeLine.charAt(i) == '#' ? 1 : 0;
        }
        return result;
    }
}

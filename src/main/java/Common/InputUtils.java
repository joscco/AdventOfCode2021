package main.java.Common;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputUtils {
    public static List<Integer> readInIntLines(InputStream stream) {
        List<String> preResult = readInStringLines(stream);
        return preResult.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public static List<String> readInBoardLines(InputStream stream) {
        List<String> result = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String currentLine;
            boolean lastLineBlank = false;
            while(true) {
                currentLine = br.readLine();
                if(isBlank(currentLine) && lastLineBlank) {
                    break;
                }
                result.add(currentLine);
                lastLineBlank = isBlank(currentLine);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File could not be found!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<String> readInStringLines(InputStream stream) {
        List<String> result = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String currentLine;
            while (!isBlank(currentLine = br.readLine())) {
                result.add(currentLine);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File could not be found!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<Integer> readInCommaSeparatedInts(InputStream stream) {
        StringBuilder preResult = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String currentLine;
            while (!isBlank(currentLine = br.readLine())) {
                preResult.append(currentLine);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File could not be found!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Arrays.stream(preResult.toString().split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }

    public static boolean isBlank(String str) {
        return str == null || str.equals("");
    }
}

package main.java.Day16;

import main.java.Common.AbstractDay;
import main.java.Common.InputUtils;
import main.java.Day5.Tuple;

import java.util.ArrayList;
import java.util.List;

public class Day extends AbstractDay {

    String binaryLine;
    Chunk chunk;

    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {
        String hexaLine = InputUtils.readInStringLines(getInputStream()).get(0);
        binaryLine = hexaToBinary(hexaLine);
        solveFirstQuestion();
        solveSecondQuestion();
    }

    private void solveFirstQuestion() {
        chunk = buildChunk(binaryLine)._0;
        System.out.println("Part 1:");
        System.out.println(chunk.getVersionSum());
    }

    private void solveSecondQuestion() {
        chunk = buildChunk(binaryLine)._0;
        System.out.println("Part 2:");
        System.out.println(chunk.getValue());
    }

    private Tuple<Chunk, Integer> buildChunk(String binaryLine) {
        Chunk chunk = new Chunk();
        chunk.version = Integer.parseInt(binaryLine.substring(0, 3), 2);
        chunk.packetType = Integer.parseInt(binaryLine.substring(3, 6), 2);
        int lastIndex;

        if (chunk.packetType == 4) {
            Tuple<Long, Integer> tuple = buildLiteral(binaryLine.substring(6));
            chunk.literal = tuple._0;
            lastIndex = 6 + tuple._1;
        } else {
            char lengthType = binaryLine.charAt(6);
            if (lengthType == '0') {
                int packageBitLength = Integer.parseInt(binaryLine.substring(7, 7 + 15), 2);
                Tuple<List<Chunk>, Integer> subChunksTuple = buildSubChunks(binaryLine.substring(7 + 15, 7 + 15 + packageBitLength), -1);
                chunk.subChunks = subChunksTuple._0;
                lastIndex = 7 + 15 + packageBitLength;
            } else {
                int numberOfPackages = Integer.parseInt(binaryLine.substring(7, 7 + 11), 2);
                Tuple<List<Chunk>, Integer> subChunksTuple= buildSubChunks(binaryLine.substring(7 + 11), numberOfPackages);
                chunk.subChunks = subChunksTuple._0;
                lastIndex = 7 + 11 + subChunksTuple._1;
            }
        }
        return new Tuple<>(chunk, lastIndex);
    }

    private Tuple<List<Chunk>, Integer> buildSubChunks(String binaryString, int numberOfPackages) {
        List<Chunk> subChunks = new ArrayList<>();
        int lastIndex = 0;
        if (numberOfPackages == -1) {
            while (binaryString.length() != 0) {
                Tuple<Chunk, Integer> chunkTuple = buildChunk(binaryString);
                Chunk chunk = chunkTuple._0;
                binaryString = binaryString.substring(chunkTuple._1);
                lastIndex += chunkTuple._1;
                subChunks.add(chunk);
            }
        } else {
            int numberPacks = 1;
            while (numberPacks <= numberOfPackages) {
                Tuple<Chunk, Integer> chunkTuple = buildChunk(binaryString);
                Chunk chunk = chunkTuple._0;
                binaryString = binaryString.substring(chunkTuple._1);
                lastIndex += chunkTuple._1;
                subChunks.add(chunk);
                numberPacks++;
            }
        }
        return new Tuple<>(subChunks, lastIndex);
    }

    private Tuple<Long, Integer> buildLiteral(String substring) {
        StringBuilder binaryRepr = new StringBuilder();
        boolean moreToGo = true;
        int index = 0;
        while (moreToGo) {
            String currentFiveBit = substring.substring( 5 * index);
            if (currentFiveBit.charAt(0) == '0') {
                moreToGo = false;
            }
            binaryRepr.append(currentFiveBit, 1, 5);
            index++;
        }
        long value = Long.parseLong(binaryRepr.toString(), 2);
        return new Tuple<>(value, index * 5);
    }

    private String hexaToBinary(String hexa) {
        StringBuilder result = new StringBuilder();
        for (char c : hexa.toCharArray()) {
            result.append(leftAppendWithZeros(Integer.toBinaryString(Integer.parseInt(c + "", 16))));
        }
        return result.toString();
    }

    private String leftAppendWithZeros(String str) {
        StringBuilder strBuilder = new StringBuilder(str);
        while (strBuilder.length() < 4) {
            strBuilder.insert(0, "0");
        }
        str = strBuilder.toString();
        return str;
    }

}

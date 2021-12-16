package main.java.Day16;

import java.util.ArrayList;
import java.util.List;

public class Chunk {
    int version;
    int packetType;
    long literal;
    List<Chunk> subChunks;

    public Chunk() {
        this.subChunks = new ArrayList<>();
    }

    public int getVersionSum() {
        int result = 0;
        if(subChunks != null && !subChunks.isEmpty()) {
            for (Chunk subchunk : subChunks) {
                result += subchunk.getVersionSum();
            }
        }
        return version + result;
    }

    public long getValue() {
        switch (packetType) {
            case 0:
                return sumOf(subChunks);
            case 1:
                return productOf(subChunks);
            case 2:
                return min(subChunks);
            case 3:
                return max(subChunks);
            case 4:
                return literal;
            case 5:
                return greater(subChunks);
            case 6:
                return less(subChunks);
            case 7:
                return equal(subChunks);
        }
        return -1L;
    }

    private long equal(List<Chunk> subChunks) {
        return subChunks.get(0).getValue() == subChunks.get(1).getValue() ? 1 : 0;
    }

    private long less(List<Chunk> subChunks) {
        return subChunks.get(0).getValue() < subChunks.get(1).getValue() ? 1 : 0;
    }

    private long greater(List<Chunk> subChunks) {
        return subChunks.get(0).getValue() > subChunks.get(1).getValue() ? 1 : 0;
    }

    private long min(List<Chunk> subChunks) {
        return subChunks.stream().map(Chunk::getValue).min(Long::compare).get();
    }

    private long max(List<Chunk> subChunks) {
        return subChunks.stream().map(Chunk::getValue).max(Long::compare).get();
    }

    private long productOf(List<Chunk> subChunks) {
        long result = 1;
        for(Chunk subchunk: subChunks) {
            result *= subchunk.getValue();
        }
        return result;
    }

    private long sumOf(List<Chunk> subChunks) {
        long result = 0;
        for(Chunk subchunk: subChunks) {
            result += subchunk.getValue();
        }
        return result;
    }
}

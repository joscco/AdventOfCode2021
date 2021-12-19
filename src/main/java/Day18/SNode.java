package main.java.Day18;

import main.java.Day5.Tuple;

import java.util.Objects;

public class SNode {

    Integer value;
    int depth;
    SNode left;
    SNode right;

    public SNode(int value, int depth) {
        this.value = value;
        this.depth = depth;
    }

    public SNode(SNode left, SNode right, int depth) {
        this.left = left;
        this.right = right;
        this.depth = depth;
    }

    public boolean hasToSplit() {
        return value != null && value >= 10 && left == null && right == null;
    }

    public void split() {
        int roundDownHalf = value / 2;
        left = new SNode(roundDownHalf, depth + 1);
        right = new SNode(value - roundDownHalf, depth + 1);
        value = null;
    }

    public SNode add(SNode next) {
        SNode newNode = new SNode(this, next, depth);
        newNode.left.increaseDepthByOne();
        newNode.right.increaseDepthByOne();
        return newNode;
    }

    public String print() {
        if(value != null) {
            return value + "";
        }

        return "[" + left.print() + "," + right.print() + "]";
    }

    public void increaseDepthByOne() {
        if (left != null) {
            left.increaseDepthByOne();
            right.increaseDepthByOne();
        }
        depth++;
    }

    public SNode reduce() {
        SNode root = this;
        while (true) {
            if (checkLeftExplosions()) {
                System.out.println("After Left Explode");
                System.out.println(root.print());
            } else if (checkRightExplosions()) {
                System.out.println("After Right Explode");
                System.out.println(root.print());
            } else if (checkLeftSplits()) {
                System.out.println("After Left Split");
                System.out.println(root.print());
            } else if (checkRightSplits()) {
                System.out.println("After Right Split");
                System.out.println(root.print());
            } else {
                break;
            }
        }
        return root;
    }

    private boolean checkRightSplits() {
        if (right.value != null) {
            if (right.hasToSplit()) {
                right.split();
                return true;
            } else {
                return false;
            }
        } else {
            return right.checkLeftSplits() || right.checkRightSplits();
        }
    }

    private boolean checkLeftSplits() {
        if (left.value != null) {
            if (left.hasToSplit()) {
                left.split();
                return true;
            } else {
                return false;
            }
        }
        return left.checkLeftSplits() || left.checkRightSplits();
    }

    private boolean checkRightExplosions() {
        Tuple<Integer, Integer> rightExplode = right.explode();
        if(rightExplode != null) {
            if(rightExplode._0 != null) {
                left.addValueRight(rightExplode._0);
            }
            return true;
        }
        return false;
    }

    private boolean checkLeftExplosions() {
        Tuple<Integer, Integer> leftExplode = left.explode();
        if(leftExplode != null) {
            if(leftExplode._1 != null) {
                right.addValueLeft(leftExplode._1);
            }
            return true;
        }
        return false;
    }

    private Tuple<Integer, Integer> explode() {
        if (value != null) {
            return null;
        }

        if (left.value != null && right.value != null) {
            // Case 0: Explodable SNode with two values
            if (depth > 4) {
                System.out.println(print());
                Tuple<Integer, Integer> result = new Tuple<>(left.value, right.value);
                value = 0;
                left = null;
                right = null;
                return result;
            }
            // Case 1: SNode with two values, everythings fine
            return null;
        }

        // Case 2: nth-Parent of Left Explodable SNode
        Tuple<Integer, Integer> leftExplosionResult = left.explode();
        if (leftExplosionResult != null) {
            if (leftExplosionResult._1 != null) {
                right.addValueLeft(leftExplosionResult._1);
                leftExplosionResult._1 = null;
            }
            return leftExplosionResult;
        }

        // Case 3: nth-Parent of Right Explodable SNode
        Tuple<Integer, Integer> rightExplosionResult = right.explode();
        if (rightExplosionResult != null) {
            if (rightExplosionResult._0 != null) {
                left.addValueRight(rightExplosionResult._0);
                rightExplosionResult._0 = null;
            }
            return rightExplosionResult;
        }

        // Case 4: no Explodable nth-children
        return null;
    }

    private void addValueRight(Integer integer) {
        if (value != null) {
            value += integer;
        } else {
            right.addValueRight(integer);
        }
    }

    private void addValueLeft(Integer integer) {
        if (value != null) {
            value += integer;
        } else {
            left.addValueLeft(integer);
        }
    }

    public int getMagnitude() {
        return Objects.requireNonNullElseGet(value, () -> 3 * left.getMagnitude() + 2 * right.getMagnitude());
    }
}

package aoc.aoc2022;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.apache.commons.lang3.tuple.MutablePair;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Day9 extends Puzzle {
    private static final Integer DAY = 9;
    private static final boolean IS_TEST = false;

    public static void main(String[] args) throws Exception {
        part1();
        part2();
    }

    public static void part1() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile(SRC_MAIN_RESOURCES + String.format(FILE_NAME, DAY, IS_TEST ? "-test" : ""), "r")) {
            String str;
            Table<Integer, Integer, Integer> bridge
                    = HashBasedTable.create();
            bridge.put(0, 0, 1);
            MutablePair<Integer, Integer> head = MutablePair.of(0, 0);
            MutablePair<Integer, Integer> tail = MutablePair.of(0, 0);

            while ((str = file.readLine()) != null) {
                String[] command = str.split(" ");
                String action = command[0];
                int steps = Integer.parseInt(command[1]);

                while (steps > 0) {
                    doAction(head, action);
                    if (!areTailHeadTouching(head, tail)) {
                        moveTail(head, tail);
                        bridge.put(tail.getLeft(), tail.getRight(), 1);
                    }
                    steps--;
                }
            }

            long answer = bridge.values().size();
            if (answer != 6197) {
                throw new Exception();
            }
            System.out.println("part1 answer " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void part2() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile(SRC_MAIN_RESOURCES + String.format(FILE_NAME, DAY, IS_TEST ? "-test" : ""), "r")) {
            String str;
            Table<Integer, Integer, Integer> bridge
                    = HashBasedTable.create();
            bridge.put(0, 0, 1);
            MutablePair<Integer, Integer> head = MutablePair.of(0, 0);
            MutablePair<Integer, Integer> tail1 = MutablePair.of(0, 0);
            MutablePair<Integer, Integer> tail2 = MutablePair.of(0, 0);
            MutablePair<Integer, Integer> tail3 = MutablePair.of(0, 0);
            MutablePair<Integer, Integer> tail4 = MutablePair.of(0, 0);
            MutablePair<Integer, Integer> tail5 = MutablePair.of(0, 0);
            MutablePair<Integer, Integer> tail6 = MutablePair.of(0, 0);
            MutablePair<Integer, Integer> tail7 = MutablePair.of(0, 0);
            MutablePair<Integer, Integer> tail8 = MutablePair.of(0, 0);
            MutablePair<Integer, Integer> tail9 = MutablePair.of(0, 0);

            while ((str = file.readLine()) != null) {
                String[] command = str.split(" ");
                String action = command[0];
                int steps = Integer.parseInt(command[1]);

                while (steps > 0) {
                    doAction(head, action);
                    if (!areTailHeadTouching(head, tail1)) {
                        moveTail(head, tail1);
                    }
                    if (!areTailHeadTouching(tail1, tail2)) {
                        moveTail(tail1, tail2);
                    }
                    if (!areTailHeadTouching(tail2, tail3)) {
                        moveTail(tail2, tail3);
                    }
                    if (!areTailHeadTouching(tail3, tail4)) {
                        moveTail(tail3, tail4);
                    }
                    if (!areTailHeadTouching(tail4, tail5)) {
                        moveTail(tail4, tail5);
                    }
                    if (!areTailHeadTouching(tail5, tail6)) {
                        moveTail(tail5, tail6);
                    }
                    if (!areTailHeadTouching(tail6, tail7)) {
                        moveTail(tail6, tail7);
                    }
                    if (!areTailHeadTouching(tail7, tail8)) {
                        moveTail(tail7, tail8);
                    }
                    if (!areTailHeadTouching(tail8, tail9)) {
                        moveTail(tail8, tail9);
                        bridge.put(tail9.getLeft(), tail9.getRight(), 1);
                    }
                    steps--;
                }
            }

            long answer = bridge.values().size();
            if (answer != 2562) {
                throw new Exception();
            }

            System.out.println("part2 answer " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void doAction(MutablePair<Integer, Integer> head, String action) {
        switch (action) {
            case "R":
                head.setRight(head.getRight() + 1);
                break;
            case "L":
                head.setRight(head.getRight() - 1);
                break;
            case "U":
                head.setLeft(head.getLeft() + 1);
                break;
            case "D":
                head.setLeft(head.getLeft() - 1);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }
    }

    private static void moveTail(MutablePair<Integer, Integer> head, MutablePair<Integer, Integer> tail) {
        //same row
        if (head.getLeft().equals(tail.getLeft())) {
            if (head.getRight() > tail.getRight()) {
                tail.setRight(tail.getRight() + 1);
            } else {
                tail.setRight(tail.getRight() - 1);
            }
        }
        //same col
        else if (head.getRight().equals(tail.getRight())) {
            if (head.getLeft() > tail.getLeft()) {
                tail.setLeft(tail.getLeft() + 1);
            } else {
                tail.setLeft(tail.getLeft() - 1);
            }
        } else {
            //snap
            if (head.getRight() > tail.getRight() && head.getLeft() > tail.getLeft()) {
                tail.setRight(tail.getRight() + 1);
                tail.setLeft(tail.getLeft() + 1);
            } else if (head.getRight() < tail.getRight() && head.getLeft() < tail.getLeft()) {
                tail.setRight(tail.getRight() - 1);
                tail.setLeft(tail.getLeft() - 1);
            } else if (head.getRight() < tail.getRight() && head.getLeft() > tail.getLeft()) {
                tail.setRight(tail.getRight() - 1);
                tail.setLeft(tail.getLeft() + 1);
            } else {
                tail.setRight(tail.getRight() + 1);
                tail.setLeft(tail.getLeft() - 1);
            }
        }
    }

    private static boolean areTailHeadTouching(MutablePair<Integer, Integer> head, MutablePair<Integer, Integer> tail) {
        return Math.abs(head.getLeft() - tail.getLeft()) < 2 && Math.abs(head.getRight() - tail.getRight()) < 2;
    }
}

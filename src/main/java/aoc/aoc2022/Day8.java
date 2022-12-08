package aoc.aoc2022;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day8 implements Puzzle {
    private static final String FILE_NAME = "day8.txt";

    public static void main(String[] args) throws Exception {
        part1();
        part2();
    }

    public static void part1() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile(SRC_MAIN_RESOURCES + FILE_NAME, "r")) {
            String str;
            Table<Integer, Integer, Integer> forest
                    = HashBasedTable.create();

            int row = 0;
            while ((str = file.readLine()) != null) {
                char[] charArray = str.toCharArray();
                for (int column = 0; column < charArray.length; column++) {
                    int height = Character.getNumericValue(charArray[column]);
                    forest.put(row, column, height);
                }
                row++;
            }

            int answer = calculateVisibleTrees(forest);

            if (answer != 1818) {
                throw new Exception();
            }

            System.out.println("answer " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void part2() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile(SRC_MAIN_RESOURCES + FILE_NAME, "r")) {
            String str;
            Table<Integer, Integer, Integer> forest
                    = HashBasedTable.create();

            int row = 0;
            while ((str = file.readLine()) != null) {
                char[] charArray = str.toCharArray();
                for (int column = 0; column < charArray.length; column++) {
                    int height = Character.getNumericValue(charArray[column]);
                    forest.put(row, column, height);
                }
                row++;
            }

            int answer = calculateScenic(forest);

            if (answer != 368368) {
                throw new Exception();
            }

            System.out.println("answer " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int calculateScenic(Table<Integer, Integer, Integer> forest) {
        int rowSize = forest.row(0).size();
        int colSize = forest.column(0).size();
        int bestScore = 1;
        for (int col = 1; col < colSize - 1; col++) {
            Collection<Integer> horizontal = forest.row(col).values();
            for (int row = 1; row < rowSize - 1; row++) {
                int current = forest.get(col, row);
                int newScore = getScore(forest, rowSize, colSize, col, horizontal, row, current);
                if (bestScore < newScore) {
                    bestScore = newScore;
                }
            }
        }
        return bestScore;
    }

    private static int getScore(Table<Integer, Integer, Integer> forest, int rowSize, int colSize, int col, Collection<Integer> horizontal, int row, int current) {
        AtomicInteger leftScore = new AtomicInteger(0);
        AtomicInteger rightScore = new AtomicInteger(0);
        AtomicInteger topScore = new AtomicInteger(0);
        AtomicInteger bottomScore = new AtomicInteger(0);

        AtomicBoolean stop = new AtomicBoolean(false);

        processRowOfTrees(rowSize, horizontal, row, current, leftScore, rightScore, stop);
        stop.set(false);

        Collection<Integer> vertical = forest.column(row).values();

        processRowOfTrees(colSize, vertical, col, current, topScore, bottomScore, stop);

        return leftScore.get() * rightScore.get() * topScore.get() * bottomScore.get();
    }

    private static void processRowOfTrees(int rowSize, Collection<Integer> horizontal, int row, int current, AtomicInteger leftTopScore, AtomicInteger rightBottomScore, AtomicBoolean stop) {
        List<Integer> left = horizontal.stream()
                .skip(0) // the offset
                .limit(row) // how many items you want
                .collect(Collectors.toList());

        new LinkedList<>(left)
                .descendingIterator()
                .forEachRemaining(n -> {
                    if (n < current && !stop.get()) {
                        leftTopScore.incrementAndGet();
                    } else if (!stop.get()) {
                        leftTopScore.incrementAndGet();
                        stop.set(true);
                    }
                });
        if (leftTopScore.get() == 0) {
            leftTopScore.incrementAndGet();
        }
        stop.set(false);

        List<Integer> right = horizontal.stream()
                .skip(row + 1) // the offset
                .limit(rowSize - row) // how many items you want
                .collect(Collectors.toList());

        new LinkedList<>(right)
                .iterator()
                .forEachRemaining(n -> {
                    if (n < current && !stop.get()) {
                        rightBottomScore.incrementAndGet();
                    } else if (!stop.get()) {
                        rightBottomScore.incrementAndGet();
                        stop.set(true);
                    }
                });
        if (rightBottomScore.get() == 0) {
            rightBottomScore.incrementAndGet();
        }
    }

    private static int calculateVisibleTrees(Table<Integer, Integer, Integer> forest) {
        int rowSize = forest.row(0).size();
        int colSize = forest.column(0).size();
        int totalVisibleTrees = rowSize * 2 + (colSize * 2 - 4);
        for (int col = 1; col < colSize - 1; col++) {
            Collection<Integer> horizontal = forest.row(col).values();
            for (int row = 1; row < rowSize - 1; row++) {
                int current = forest.get(col, row);
                if (isTreeVisible(forest, rowSize, colSize, col, horizontal, row, current)) {
                    totalVisibleTrees++;
                }
            }
        }
        return totalVisibleTrees;
    }

    private static boolean isTreeVisible(Table<Integer, Integer, Integer> forest, int rowSize, int colSize, int col, Collection<Integer> horizontal, int row, int current) {
        if (processVisible(rowSize, horizontal, row, current)) return true;
        Collection<Integer> vertical = forest.column(row).values();
        return processVisible(colSize, vertical, col, current);
    }

    private static boolean processVisible(int rowSize, Collection<Integer> horizontal, int row, int current) {
        Set<Integer> left = horizontal.stream()
                .skip(0) // the offset
                .limit(row) // how many items you want
                .collect(Collectors.toSet());
        if (left.stream().noneMatch(a -> a >= current)) {
            return true;
        }
        Set<Integer> right = horizontal.stream()
                .skip(row + 1) // the offset
                .limit(rowSize - row) // how many items you want
                .collect(Collectors.toSet());
        return right.stream().noneMatch(a -> a >= current);
    }
    
}

package aoc.aoc2022;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;


public class Day12 extends Puzzle {
    private static final Integer DAY = 12;
    private static final boolean IS_TEST = false;

    public static void main(String[] args) throws Exception {
        part1();
        part2();
    }

    @Getter
    @NoArgsConstructor
    private static class Area {
        private List<List<Square>> rows = new ArrayList<>();
        private Square start;
        private Square end;
        private int numberOfColumns = -1;

        public void addRow(String row) {
            if (numberOfColumns == -1) {
                numberOfColumns = row.length();
            }
            int numberOfRows = rows.size();
            List<Square> rowOfSquares = new ArrayList<>();
            for (int i = 0; i < row.length(); i++) {
                char character = row.charAt(i);
                Square square = new Square(numberOfRows, i, character);
                if (character == 'S') {
                    start = square;
                }
                if (character == 'E') {
                    end = square;
                }
                rowOfSquares.add(square);
            }
            rows.add(rowOfSquares);
        }

        public Square getSquare(int row, int col) {
            return rows.get(row).get(col);
        }

        public int numRows() {
            return rows.size();
        }

        public List<Square> getAllSquares(char height) {
            List<Square> startingSquares = new LinkedList<>();
            for (List<Square> row : rows) {
                for (Square square : row) {
                    if (square.height == height) {
                        startingSquares.add(square);
                    }
                }
            }
            return startingSquares;
        }
        public void resetDistance() {
            rows.stream().flatMap(Collection::stream).forEach(Square::resetDistance);
        }
    }

    private static class Square implements Comparable<Square> {
        private final int row;
        private final int column;
        private final char height;
        private int distance;

        public Square(int row, int column, char c) {
            this.row = row;
            this.column = column;
            switch (c) {
                case 'S':
                    height = 'a';
                    break;
                case 'E':
                    height = 'z';
                    break;
                default:
                    height = c;
                    break;
            }
        }

        @Override
        public int compareTo(Square s) {
            return Integer.compare(distance, s.distance);
        }

        public void resetDistance() {
            distance = Integer.MAX_VALUE;
        }
    }

    public static void part1() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile(SRC_MAIN_RESOURCES + String.format(FILE_NAME, DAY, IS_TEST ? "-test" : ""), "r")) {
            String str;
            Area area = new Area();

            while ((str = file.readLine()) != null) {
                area.addRow(str);
            }

            Square start = area.getStart();
            Square end = area.getEnd();

            int answer = getShortestDistance(area, start, end);

            if (answer != 449) {
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
            Area area = new Area();

            while ((str = file.readLine()) != null) {
                area.addRow(str);
            }

            Square end = area.getEnd();

            int answer = Integer.MAX_VALUE;
            for (Square potential : area.getAllSquares('a')) {
                answer = Math.min(answer, getShortestDistance(area, potential, end));
            }

            if (answer != 443) {
                throw new Exception();
            }
            System.out.println("part1 answer " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static int getShortestDistance(Area area, Square start, Square end) {
        area.resetDistance();
        start.distance = 0;
        PriorityQueue<Square> queue = new PriorityQueue<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            Square square = queue.remove();

            if (square.row == end.row && square.column == end.column) {
                return square.distance;
            }

            visitSquare(square, square.row, square.column - 1, area, queue);
            visitSquare(square, square.row, square.column + 1, area, queue);
            visitSquare(square, square.row - 1, square.column, area, queue);
            visitSquare(square, square.row + 1, square.column, area, queue);
        }
        return Integer.MAX_VALUE;
    }

    private static void visitSquare(Square currentSquare, int row, int col, Area area, PriorityQueue<Square> queue) {
        if (row == -1 || col == -1 || row == area.numRows() || col == area.getNumberOfColumns()) {
            return;
        }
        Square neighbor = area.getSquare(row, col);
        if (neighbor.height - currentSquare.height > 1) {
            return;
        }
        int pathLen = currentSquare.distance + 1;
        if (pathLen < neighbor.distance) {
            neighbor.distance = pathLen;
            queue.remove(neighbor);
            queue.add(neighbor);
        }
    }
}

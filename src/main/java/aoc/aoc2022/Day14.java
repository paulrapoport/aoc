package aoc.aoc2022;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Comparator;


public class Day14 extends Puzzle {
    private static final Integer DAY = 14;
    private static final boolean IS_TEST = false;

    public static void main(String[] args) throws Exception {
        part1();
        part2();
    }

    public static void part1() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile(SRC_MAIN_RESOURCES + String.format(FILE_NAME, DAY, IS_TEST ? "-test" : ""), "r")) {
            String str;
            Table<Integer, Integer, Character> cave
                    = HashBasedTable.create();

            while ((str = file.readLine()) != null) {
                String[] a = str.split(" -> ");
                int beginningPointX = -1;
                int beginningPointY = -1;
                for (int i = 0; i < a.length; i++) {
                    String[] b = a[i].split(",");
                    if (i > 0) {
                        for (int m = Math.min(beginningPointX, Integer.parseInt(b[0])); m <= Math.max(beginningPointX, Integer.parseInt(b[0])); m++) {
                            for (int n = Math.min(beginningPointY, Integer.parseInt(b[1])); n <= Math.max(beginningPointY, Integer.parseInt(b[1])); n++) {
                                cave.put(m, n, '#');
                            }
                        }
                    }
                    beginningPointX = Integer.parseInt(b[0]);
                    beginningPointY = Integer.parseInt(b[1]);
                }
            }
            int bottom = cave.columnKeySet().stream().max(Comparator.naturalOrder()).get();

            boolean abyss = false;
            while (!abyss) {
                abyss = dropIntoAbyss(500, 0, cave, bottom);
            }
            long answer = cave.values().stream().filter(a -> a == 'o').count();

            if (answer != 799) {
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
            Table<Integer, Integer, Character> cave
                    = HashBasedTable.create();

            while ((str = file.readLine()) != null) {
                String[] a = str.split(" -> ");
                int beginningPointX = -1;
                int beginningPointY = -1;
                for (int i = 0; i < a.length; i++) {
                    String[] b = a[i].split(",");
                    if (i > 0) {
                        for (int m = Math.min(beginningPointX, Integer.parseInt(b[0])); m <= Math.max(beginningPointX, Integer.parseInt(b[0])); m++) {
                            for (int n = Math.min(beginningPointY, Integer.parseInt(b[1])); n <= Math.max(beginningPointY, Integer.parseInt(b[1])); n++) {
                                cave.put(m, n, '#');
                            }
                        }
                    }
                    beginningPointX = Integer.parseInt(b[0]);
                    beginningPointY = Integer.parseInt(b[1]);
                }
            }
            int bottom = cave.columnKeySet().stream().max(Comparator.naturalOrder()).get();
            int widest = cave.rowKeySet().stream().max(Comparator.naturalOrder()).get();

            for (int bot = 0; bot <= widest + widest; bot++) {
                cave.put(bot, bottom + 2, '#');
            }

            boolean abyss = false;
            while (!abyss) {
                abyss = dropIntoAbyss(500, 0, cave, bottom + 2);
            }
            long answer = cave.values().stream().filter(a -> a == 'o').count();

            if (answer != 29076) {
                throw new Exception();
            }
            System.out.println("part1 answer " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean dropIntoAbyss(int x, int y, Table<Integer, Integer, Character> cave, int bottom) {
        if (cave.get(x, y) != null) {
            return true;
        }
        while (y < bottom) {
            if (cave.get(x, y + 1) == null) {
                y++;
            } else if (cave.get(x - 1, y + 1) == null) {
                y++;
                x--;
            } else if (cave.get(x + 1, y + 1) == null) {
                y++;
                x++;
            } else {
                cave.put(x, y, 'o');
                return false;
            }
        }
        return true;
    }

}

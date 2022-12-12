package aoc.aoc2022;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.stream.IntStream;

public class Day10 extends Puzzle {
    private static final Integer DAY = 10;
    private static final boolean IS_TEST = false;
    public static final Set<Integer> SIGNALS_TO_CHECK = new HashSet<>(Arrays.asList(20, 60, 100, 140, 180, 220));

    public static void main(String[] args) throws Exception {
        part1();
        part2();
    }

    public static void part1() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile(SRC_MAIN_RESOURCES + String.format(FILE_NAME, DAY, IS_TEST ? "-test" : ""), "r")) {
            String str;
            int cycle = 1;
            int signal = 1;
            Map<Integer, Integer> futureInstructions = new HashMap<>();
            List<Integer> answers = new ArrayList<>();

            while ((str = file.readLine()) != null) {
                if (futureInstructions.containsKey(cycle)) {
                    signal = signal + futureInstructions.get(cycle);
                    futureInstructions.remove(cycle);
                }
                if (SIGNALS_TO_CHECK.contains(cycle)) {
                    answers.add(cycle * signal);
                }
                if (str.startsWith("addx")) {
                    String[] instruction = str.split(" ");
                    int shift = Integer.parseInt(instruction[1]);
                    futureInstructions.put(cycle + 2, shift);
                    cycle++;
                    if (futureInstructions.containsKey(cycle)) {
                        signal = signal + futureInstructions.get(cycle);
                        futureInstructions.remove(cycle);
                    }
                    if (SIGNALS_TO_CHECK.contains(cycle)) {
                        answers.add(cycle * signal);
                    }
                }
                cycle++;
            }

            long answer = answers.stream().flatMapToInt(IntStream::of).sum();
            if (answer != 13680) {
                throw new Exception();
            }
            System.out.println("part1 answer " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void part2() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile(SRC_MAIN_RESOURCES + String.format(FILE_NAME, DAY, IS_TEST ? "b-test" : "b"), "r")) {
            int x = 1;
            System.out.println("part2 answer ");
            for (int i = 0; i < 240; ++i) {
                if (i % 40 == 0) {
                    System.out.println();
                }
                if (i % 5 == 0) {
                    System.out.print(" ");
                }
                if (x == i % 40 || x + 1 == i % 40 || x - 1 == i % 40) {
                    System.out.print("@");
                } else {
                    System.out.print(" ");
                }

                String line = file.readLine();
                String[] tokens = line.split(" ");
                if (tokens[0].equals("addx")) {
                    x += Integer.parseInt(tokens[1]);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

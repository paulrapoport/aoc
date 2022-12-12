package aoc.aoc2022;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day11 extends Puzzle {
    private static final Integer DAY = 11;
    private static final boolean IS_TEST = false;

    @Getter
    @Setter
    @NoArgsConstructor
    static class Monkey {
        long times = 1;
        long plus = 0;
        long inspected = 0;
        int ifTrue = 0;
        int ifFalse = 0;
        long test = 1;
        boolean timesSelf = false;
        boolean plusSelf = false;
        Queue<Long> items = new LinkedList<>();
    }

    public static void main(String[] args) throws Exception {
        part1();
        part2();
    }

    public static void part1() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile(SRC_MAIN_RESOURCES + String.format(FILE_NAME, DAY, IS_TEST ? "-test" : ""), "r")) {
            String str;
            List<Monkey> monkeys = new LinkedList<>();

            while ((str = file.readLine()) != null) {
                if (str.startsWith("Monkey")) {
                    Monkey monkey = new Monkey();
                    monkeys.add(monkey);
                } else if (str.contains("Starting items")) {
                    String[] a = str.substring(18).split(", ");
                    for (String s : a) {
                        monkeys.get(monkeys.size() - 1).getItems().add((long) Integer.parseInt(s));
                    }
                } else if (str.contains("Operation")) {
                    if (str.endsWith("* old")) {
                        monkeys.get(monkeys.size() - 1).setTimesSelf(true);
                    } else if (str.endsWith("+ old")) {
                        monkeys.get(monkeys.size() - 1).setPlusSelf(true);
                    } else if (str.contains("*")) {
                        monkeys.get(monkeys.size() - 1).setTimes(Integer.parseInt(str.substring(25)));
                    } else {
                        monkeys.get(monkeys.size() - 1).setPlus(Integer.parseInt(str.substring(25)));
                    }
                } else if (str.contains("Test")) {
                    monkeys.get(monkeys.size() - 1).setTest(Integer.parseInt(str.substring(21)));
                } else if (str.contains("If true")) {
                    monkeys.get(monkeys.size() - 1).setIfTrue(Integer.parseInt(str.substring(29)));
                } else if (str.contains("If false")) {
                    monkeys.get(monkeys.size() - 1).setIfFalse(Integer.parseInt(str.substring(30)));
                }
            }

            for (int iterations = 0; iterations < 20; iterations++) {
                for (Monkey m : monkeys) {
                    while (!m.getItems().isEmpty()) {
                        long item = m.getItems().remove();
                        item = item + m.getPlus();
                        item = item * m.getTimes();
                        if (m.plusSelf) {
                            item = item + item;
                        }
                        if (m.timesSelf) {
                            item = item * item;
                        }
                        item = (int) Math.floor(item / 3);
                        if (item % m.getTest() == 0) {
                            monkeys.get(m.getIfTrue()).getItems().add(item);
                        } else {
                            monkeys.get(m.getIfFalse()).getItems().add(item);
                        }
                        m.setInspected(m.getInspected() + 1);
                    }
                }
            }

            long answer = getAnswer(monkeys);
            if (answer != 120384) {
                throw new Exception();
            }
            System.out.println("part1 answer " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Long getAnswer(List<Monkey> monkeys) {
        List<Long> setVisited = monkeys.stream()
                .flatMap(p -> Stream.of(p.getInspected()))
                .collect(Collectors.toList());

        List<Long> topTwo = setVisited.stream()
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .collect(Collectors.toList());
        long answer = 1;
        for (long a : topTwo) {
            answer = answer * a;
        }
        return answer;
    }

    public static void part2() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile(SRC_MAIN_RESOURCES + String.format(FILE_NAME, DAY, IS_TEST ? "-test" : ""), "r")) {
            String str;
            List<Monkey> monkeys = new LinkedList<>();

            while ((str = file.readLine()) != null) {
                if (str.startsWith("Monkey")) {
                    Monkey monkey = new Monkey();
                    monkeys.add(monkey);
                } else if (str.contains("Starting items")) {
                    String[] a = str.substring(18).split(", ");
                    for (String s : a) {
                        monkeys.get(monkeys.size() - 1).getItems().add((long) Integer.parseInt(s));
                    }
                } else if (str.contains("Operation")) {
                    if (str.endsWith("* old")) {
                        monkeys.get(monkeys.size() - 1).setTimesSelf(true);
                    } else if (str.endsWith("+ old")) {
                        monkeys.get(monkeys.size() - 1).setPlusSelf(true);
                    } else if (str.contains("*")) {
                        monkeys.get(monkeys.size() - 1).setTimes(Integer.parseInt(str.substring(25)));
                    } else {
                        monkeys.get(monkeys.size() - 1).setPlus(Integer.parseInt(str.substring(25)));
                    }
                } else if (str.contains("Test")) {
                    monkeys.get(monkeys.size() - 1).setTest(Integer.parseInt(str.substring(21)));
                } else if (str.contains("If true")) {
                    monkeys.get(monkeys.size() - 1).setIfTrue(Integer.parseInt(str.substring(29)));
                } else if (str.contains("If false")) {
                    monkeys.get(monkeys.size() - 1).setIfFalse(Integer.parseInt(str.substring(30)));
                }
            }

            long combinedFactor = 1;
            for (Monkey monkey : monkeys) {
                combinedFactor *= monkey.getTest();
            }

            for (int iterations = 0; iterations < 10000; iterations++) {
                for (Monkey m : monkeys) {
                    while (!m.getItems().isEmpty()) {
                        long item = m.getItems().remove();
                        item = item + m.getPlus();
                        item = item * m.getTimes();
                        if (m.plusSelf) {
                            item += item;
                        }
                        if (m.timesSelf) {
                            item *= item;
                        }
                        item = item % combinedFactor;
                        if (item % m.getTest() == 0) {
                            monkeys.get(m.getIfTrue()).getItems().add(item);
                        } else {
                            monkeys.get(m.getIfFalse()).getItems().add(item);
                        }
                        m.setInspected(m.getInspected() + 1);
                    }
                }
            }

            long answer = getAnswer(monkeys);
            if (answer != 32059801242L) {
                throw new Exception();
            }
            System.out.println("part2 answer " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

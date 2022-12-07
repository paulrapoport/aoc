package aoc;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

public class Day5 {

    public static void main(String[] args) throws Exception {
        part1();
        part2();
    }

    public static void part1() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile("src/main/resources/day5.txt", "r")) {
            String str;
            LinkedList<LinkedList<Character>> stacks = new LinkedList<>();

            while ((str = file.readLine()) != null) {
                if (str.contains("[")) {
                    int currentStackIndex = 0;
                    int index = 1;
                    while (index < str.length()) {
                        char ch = str.charAt(index);
                        LinkedList<Character> currentStack;
                        try {
                            currentStack = stacks.get(currentStackIndex);
                        } catch (Exception a) {
                            currentStack = new LinkedList<>();
                            stacks.add(currentStackIndex, currentStack);
                        }
                        if (ch != ' ') {
                            currentStack.add(ch);
                        }
                        index = index + 4;
                        currentStackIndex++;
                    }
                } else if (str.contains("move")) {
                    String[] a = str.split(" ");
                    int howMany = Integer.parseInt(a[1]);
                    int stackFromIndex = Integer.parseInt(a[3]) - 1;
                    int stackToIndex = Integer.parseInt(a[5]) - 1;
                    while (howMany > 0) {
                        Character charToMove = stacks.get(stackFromIndex).poll();
                        stacks.get(stackToIndex).push(charToMove);
                        howMany--;
                    }
                }

            }
            StringBuilder answer = new StringBuilder();
            for (LinkedList<Character> temp : stacks) {
                answer.append(temp.peekFirst());
            }
            if (!answer.toString().equals("SHQWSRBDL")) {
                throw new Exception();
            }
            System.out.println("answer " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void part2() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile("src/main/resources/day5.txt", "r")) {
            String str;
            LinkedList<LinkedList<Character>> stacks = new LinkedList<>();

            while ((str = file.readLine()) != null) {

                if (str.contains("[")) {
                    int currentStackIndex = 0;
                    int index = 1;
                    while (index < str.length()) {
                        char ch = str.charAt(index);
                        LinkedList<Character> currentStack;
                        try {
                            currentStack = stacks.get(currentStackIndex);
                        } catch (Exception a) {
                            currentStack = new LinkedList<>();
                            stacks.add(currentStackIndex, currentStack);
                        }
                        if (ch != ' ') {
                            currentStack.add(ch);
                        }
                        index = index + 4;
                        currentStackIndex++;
                    }
                } else if (str.contains("move")) {
                    String[] a = str.split(" ");
                    int howMany = Integer.parseInt(a[1]);
                    int stackFromIndex = Integer.parseInt(a[3]) - 1;
                    int stackToIndex = Integer.parseInt(a[5]) - 1;
                    LinkedList<Character> tempStorage = new LinkedList<>();
                    while (howMany > 0) {
                        Character charToMove = stacks.get(stackFromIndex).poll();
                        tempStorage.push(charToMove);
                        howMany--;
                    }
                    for (Character temp : tempStorage) {
                        stacks.get(stackToIndex).push(temp);
                    }
                }
            }
            StringBuilder answer = new StringBuilder();
            for (LinkedList<Character> temp : stacks) {
                answer.append(temp.peekFirst());
            }
            if (!answer.toString().equals("CDTQZHBRS")) {
                throw new Exception();
            }
            System.out.println("answer " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

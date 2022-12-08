package aoc.aoc2022;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Set;

public class Day6 {
    private static final String FILE_NAME = "day6.txt";
    public static final String PATH = "src/main/resources/aoc2022/";

    public static void main(String[] args) throws Exception {
        part1();
        part2();
    }

    public static void part1() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile(PATH + FILE_NAME, "r")) {
            String str;
            int returnIndex = -1;

            while ((str = file.readLine()) != null) {
                returnIndex = getPacketIndex(str, 4);
            }
            if (returnIndex != 1651) {
               throw new Exception();
            }
            System.out.println("answer " + returnIndex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void part2() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile(PATH + FILE_NAME, "r")) {
            String str;
            int returnIndex = 0;
            while ((str = file.readLine()) != null) {
                returnIndex = getPacketIndex(str, 14);
            }
            if (returnIndex != 3837) {
                throw new Exception();
            }
            System.out.println("answer " + returnIndex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getPacketIndex(String str, int packetSize) {
        for (int index = 0; index < str.length(); index = index + 1) {
            Set<Character> packet = new HashSet<>();
            if (index + packetSize < str.length()) {
                int packetIndex = 0;
                while (packetIndex < packetSize) {
                    packet.add(str.charAt(index + packetIndex));
                    packetIndex++;
                }
                if (packet.size() == packetSize) {
                    return index + packetSize;
                }
            }
        }
        return -1;
    }
}

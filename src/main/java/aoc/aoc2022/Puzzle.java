package aoc.aoc2022;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class Puzzle {
    public static String SRC_MAIN_RESOURCES = "src/main/resources/aoc2022/";
    public static String FILE_NAME = "day%d%s.txt";
    RandomAccessFile inputFile;

    static RandomAccessFile getFile(int day) throws Exception {

        String a=  String.format(FILE_NAME, day, "-test");
        try (RandomAccessFile file = new RandomAccessFile(SRC_MAIN_RESOURCES + String.format(FILE_NAME, day, "-test"), "r")) {
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

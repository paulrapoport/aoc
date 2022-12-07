package aoc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Day7 {
    private static final String FILE_NAME = "day7-test.txt";
    public static final String SRC_MAIN_RESOURCES = "src/main/resources/";

    private static List<Integer> ALL_SIZES = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        part1();
        part2();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class Directory {
        String name;
        int size = 0;
        List<Directory> subDirectories = new ArrayList<>();

        public Directory getSubDirectory(String subDir) {
            return subDirectories.stream().filter(a -> a.getName().equals(subDir)).findFirst().get();
        }

        public int getTotalSize() {
            int totalSize = this.size;
            for (Directory dir : subDirectories) {
                totalSize = totalSize + dir.getTotalSize();
            }
            ALL_SIZES.add(totalSize);
            return totalSize;
        }
    }

    public static void part1() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile(SRC_MAIN_RESOURCES + FILE_NAME, "r")) {
            String str;
            Directory root = new Directory();
            root.setName("/");

            Deque<Directory> stack = new LinkedList<>();

            while ((str = file.readLine()) != null) {
                processInput(str, root, stack);
            }

            root.getTotalSize();

            int answer = ALL_SIZES.stream().filter(a -> a <= 100_000 && a != 0).reduce(0, Integer::sum);

            if (answer != 95437) {
                throw new Exception();
            }
            System.out.println("answer " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void processInput(String str, Directory root, Deque<Directory> stack) {
        if (str.startsWith("$ cd /")) {
            stack.push(root);
        } else if (str.startsWith("$ cd ..")) {
            stack.pop();
        } else if (str.startsWith("$ cd ")) {
            stack.push(stack.peek().getSubDirectory(str.substring(5)));
        } else if (str.startsWith("dir ")) {
            Directory newDir = new Directory();
            newDir.setName(str.substring(4));
            stack.peek().getSubDirectories().add(newDir);
        } else if (!str.startsWith("$ ls")) {
            String[] a = str.split(" ");
            int fileSize = Integer.parseInt(a[0]);
            stack.peek().setSize(stack.peek().getSize() + fileSize);
        }
    }

    public static void part2() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile(SRC_MAIN_RESOURCES + FILE_NAME, "r")) {
            String str;
            Directory root = new Directory();
            root.setName("/");

            Deque<Directory> stack = new LinkedList<>();

            while ((str = file.readLine()) != null) {
                processInput(str, root, stack);
            }

            int rootSize = root.getTotalSize();
            int freeSpace = 70_000_000 - rootSize;
            int freeSpaceNeeded = 30_000_000 - freeSpace;

            int answer = ALL_SIZES.stream().filter(a -> a >= freeSpaceNeeded).min(Integer::compare).get();
            if (answer != 24933642) {
                throw new Exception();
            }
            System.out.println("answer " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

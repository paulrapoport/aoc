package aoc.aoc2022;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Day4 {

    public static void main(String[] args) throws Exception {
        part1();
        part2();
    }

    public static void part1() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile("src/main/resources/day4.txt", "r")) {
            String str;
            int sum = 0;

            while ((str = file.readLine()) != null) {

                String[] a = str.split(",");
                String[] left = a[0].split("-");
                int left1 = Integer.parseInt(left[0]);
                int left2 = Integer.parseInt(left[1]);
                String[] right = a[1].split("-");
                int right1 = Integer.parseInt(right[0]);
                int right2 = Integer.parseInt(right[1]);

                if (left1 <= right1 && left2 >= right2 || left1 >= right1 && left2 <= right2) {
                    sum++;
                }
            }
            if (sum != 456) {
                throw new Exception();
            }
            System.out.println("sum " + sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void part2() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile("src/main/resources/day4.txt", "r")) {
            String str;
            int sum = 0;

            while ((str = file.readLine()) != null) {

                String[] a = str.split(",");
                String[] left = a[0].split("-");
                int left1 = Integer.parseInt(left[0]);
                int left2 = Integer.parseInt(left[1]);
                String[] right = a[1].split("-");
                int right1 = Integer.parseInt(right[0]);
                int right2 = Integer.parseInt(right[1]);

                if ((left1 >= right1 && left1 <= right2) ||
                        (left2 >= right1 && left2 <= right2) ||
                        (right1 >= left1 && right1 <= left2) ||
                        (right2 >= left1 && right2 <= left2)) {
                    sum++;
                }

            }
            if (sum != 808) {
                throw new Exception();
            }
            System.out.println("sum " + sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

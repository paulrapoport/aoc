package aoc.aoc2022;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class Day3 {

    public static void main(String[] args) throws Exception {
        try {
            int score;
            try (RandomAccessFile file = new RandomAccessFile("src/main/resources/day3.txt", "r")) {
                score = 0;
                String str;
                while ((str = file.readLine()) != null) {
                    String sameLetter = "";
                    String fistPocket = str.substring(0, str.length() / 2);
                    String secondPocket = str.substring(str.length() / 2);
                    sameLetter = findSameLetters(fistPocket, secondPocket);
                    score += getLetterValue(sameLetter);
                }
            }
            if (score != 8053) {
                throw new Exception();
            }
            System.out.println("part1: " + score);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //part 2
        try {
            int score;
            try (RandomAccessFile file = new RandomAccessFile("src/main/resources/day3.txt", "r")) {
                score = 0;
                String str;
                while ((str = file.readLine()) != null) {
                    String secondLine = file.readLine();
                    String thirdLine = file.readLine();
                    String sameLetter = findSameLetters(thirdLine, findSameLetters(str, secondLine));
                    score += getLetterValue(sameLetter);
                }
            }
            if (score != 2425) {
                throw new Exception();
            }
            System.out.println("part2: " + score);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static int getLetterValue(String letter) {
        int score = letter.codePointAt(0);
        if (score >= 65 && score <= 90) {
            score -= 64 - 26;
        } else {
            score -= 96;
        }
        return score;
    }

    private static String findSameLetters(String fist, String second) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < fist.length(); i++) {
            String fistChar = String.valueOf(fist.charAt(i));
            for (int j = 0; j < second.length(); j++) {
                String secondChar = String.valueOf(second.charAt(j));
                if (fistChar.equals(secondChar)) {
                    s.append(fistChar);
                }
            }
        }
        return s.toString();
    }
}
package aoc2022.day2;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Day2 {

    private static final Map<String, Integer> SCORES_1 = Map.of(
            "A X", 4,
            "A Y", 8,
            "A Z", 3,
            "B X", 1,
            "B Y", 5,
            "B Z", 9,
            "C X", 7,
            "C Y", 2,
            "C Z", 6
    );

    private static final Map<String, Integer> SCORES_2 = Map.of(
            "A X", 3,
            "A Y", 4,
            "A Z", 8,
            "B X", 1,
            "B Y", 5,
            "B Z", 9,
            "C X", 2,
            "C Y", 6,
            "C Z", 7
    );

    public static void main(String[]args) throws IOException {
        List<String> lines = Utils.readLines(args);

        System.out.println("Total score 1: " + countTotalScore(lines, SCORES_1));
        System.out.println("Total score 2: " + countTotalScore(lines, SCORES_2));
    }

    private static int countTotalScore(List<String> lines, Map<String, Integer> scores) {
        int total = 0;

        for (String line: lines) {
            total += scores.get(line);
        }

        return total;
    }
}
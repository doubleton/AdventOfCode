package aoc2022.day3;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day3 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Utils.readLines(args);

        System.out.println(totalPriorities(lines));
        System.out.println(sumOfBadges(lines));
    }

    private static int totalPriorities(List<String> lines) {
        int total = 0;
        for (String line: lines) {
            total += charToPriority(findCommonInHalves(line));
        }
        return total;
    }

    private static int sumOfBadges(List<String> lines) {
        int total = 0;
        while (!lines.isEmpty()) {
            total += charToPriority(findCommon(lines.remove(0), lines.remove(0), lines.remove(0)));
        }

        return total;
    }

    private static char findCommon(String... lines) {
        Set<Character> set = toCharSet(lines[0]);
        for (int i = 1; i < lines.length; i++) {
            set.retainAll(toCharSet(lines[i]));
        }
        return set.iterator().next();
    }

    private static char findCommonInHalves(String line) {
        String firstHalf = line.substring(0, line.length() / 2);
        String secondHalf = line.substring(line.length() / 2);

        return findCommon(firstHalf, secondHalf);
    }

    private static Set<Character> toCharSet(String string) {
        return string.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
    }

    private static int charToPriority(char item) {
        if (item >= 65 && item <= 90) {
            return item - 38;
        }
        if (item >= 97 && item <= 122) {
            return item - 96;
        }
        throw new RuntimeException("Unknown char: " + item);
    }

}

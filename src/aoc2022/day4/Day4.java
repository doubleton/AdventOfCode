package aoc2022.day4;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Day4 {

    public static void main(String[] args) throws IOException {
        List<String> lines = Utils.readLines(args);

        List<Pair<Assignment>> assignmentPairs = parseAssignments(lines);

        System.out.println(calcFullyContained(assignmentPairs));
        System.out.println(calcOverlapped(assignmentPairs));
    }

    private static int calcOverlapped(List<Pair<Assignment>> assignmentPairs) {
        int total = 0;
        for (Pair<Assignment> pair : assignmentPairs) {
            if (pair.first.overlap(pair.second)) {
                total += 1;
            }
        }
        return total;
    }

    public static int calcFullyContained(List<Pair<Assignment>> assignmentPairs) {
        int total = 0;
        for (Pair<Assignment> pair : assignmentPairs) {
            if (Assignment.fillyContain(pair.first, pair.second)) {
                total += 1;
            }
        }

        return total;
    }

    private static List<Pair<Assignment>> parseAssignments(List<String> lines) {
        return lines.stream()
                .map(line -> line.split(","))
                .map(parts -> new Pair<>(new Assignment(parts[0]), new Assignment(parts[1])))
                .collect(Collectors.toList());
    }

}

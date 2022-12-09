package aoc2022.day1;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day1 {

    public static void main(String[]args) throws IOException {
        List<String> lines = Utils.readLines(args);

        System.out.println("Max calories: " + countMaxCalories(lines));
        System.out.println("Top 3 calories: " + countTop3MaxCalories(lines));
    }

    private static int countMaxCalories(List<String> lines) {
        int max = 0;
        int currentElfTotal = 0;
        for (String line: lines) {
            if (line.isEmpty()) {
                if (currentElfTotal > max) {
                    max = currentElfTotal;
                }
                currentElfTotal = 0;
                continue;
            }
            currentElfTotal += Integer.parseInt(line);
        }

        if (currentElfTotal > max) {
            max = currentElfTotal;
        }

        return max;
    }

    private static int countTop3MaxCalories(List<String> lines) {
        List<Integer> elvesTotal = new ArrayList<>();
        int currentElfTotal = 0;
        for (String line: lines) {
            if (line.isEmpty()) {
                elvesTotal.add(currentElfTotal);
                currentElfTotal = 0;
                continue;
            }
            currentElfTotal += Integer.parseInt(line);
        }

        elvesTotal.add(currentElfTotal);

        return elvesTotal.stream().sorted(Comparator.reverseOrder()).limit(3).mapToInt(Integer::intValue).sum();
    }
}
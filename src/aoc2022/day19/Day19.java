package aoc2022.day19;

import aoc2022.common.Utils;

import java.io.IOException;
import java.util.List;

public class Day19 {

    public static void main(String[] args) throws IOException {
//        calcTotalQualityLevel(Utils.readTestInput("day19"));
//        calcTotalQualityLevel(Utils.readInput("day19"));

//        calcMaxGeodesMultiplication(Utils.readTestInput("day19"));
        calcMaxGeodesMultiplication(Utils.readInput("day19"));
    }

    private static void calcTotalQualityLevel(List<String> input) {
        int totalQualityLevel = input.parallelStream()
                .map(Blueprint::parse)
                .map(RobotFactory::new)
                .map(factory -> factory.calcQualityLevel(24))
                .reduce(0, Integer::sum);
        System.out.println(totalQualityLevel);
    }

    private static void calcMaxGeodesMultiplication(List<String> input) {
        int maxGeodesMultiplication = input.subList(0, Integer.min(input.size(), 3)).parallelStream()
                .map(Blueprint::parse)
                .map(RobotFactory::new)
                .map(factory -> factory.calcMaxGeodes(32))
                .reduce(1, (a, b) -> a * b);
        System.out.println(maxGeodesMultiplication);
    }
}

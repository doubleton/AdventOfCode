package aoc2022.day24;

import aoc2022.common.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Part2 {

    static void main(String[] args) throws IOException {
        List<String> input = loadInput(args);
        ValleyMap valleyMap = ValleyMap.init(input);
        int totalMinutes = valleyMap.solvePart2();

        System.out.println("Total minutes (start -> goal -> start -> goal): " + totalMinutes);
    }

    private static List<String> loadInput(String[] args) throws IOException {
        if (args.length > 0) {
            if ("test".equalsIgnoreCase(args[0])) {
                return Utils.readTestInput("day24");
            }
            return Files.readAllLines(Path.of(args[0]));
        }
        return Utils.readInput("day24");
    }
}
